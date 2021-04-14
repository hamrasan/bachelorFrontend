package cz.fel.cvut.hamrasan.gardener.service;

import com.fasterxml.jackson.core.type.TypeReference;
import cz.fel.cvut.hamrasan.gardener.exceptions.NotAllowedException;
import cz.fel.cvut.hamrasan.gardener.model.Device;
import cz.fel.cvut.hamrasan.gardener.model.DeviceStatus;
import cz.fel.cvut.hamrasan.gardener.model.MapperDevice;
import cz.fel.cvut.hamrasan.gardener.model.TokenRequestEntity;
import okhttp3.*;
import org.apache.commons.codec.binary.Hex;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.io.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;


import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ValveService {
    private static final ObjectMapper mapper = new ObjectMapper();
    private TokenRequestEntity entity = null;
    private Device device = null;
    private MapperDevice mapperDevice = null;


    public ValveService() throws IOException, NoSuchAlgorithmException, InvalidKeyException {

    }

    public String calcSing(String cliendId, String secret, String timestamp) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        String str = cliendId + timestamp;
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        byte[] hmacSha256 = null;
        hmacSha256 = sha256_HMAC.doFinal(str.getBytes());

        String hash = Base64.encodeBase64String(sha256_HMAC.doFinal(str.getBytes()));
        return Hex.encodeHexString(sha256_HMAC.doFinal(str.getBytes("UTF-8"))).toUpperCase();
    }

    public String calcSing(String cliendId, String accessToken, String secret, String timestamp) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        String str = cliendId + accessToken + timestamp;
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        byte[] hmacSha256 = null;
        hmacSha256 = sha256_HMAC.doFinal(str.getBytes());

        String hash = Base64.encodeBase64String(sha256_HMAC.doFinal(str.getBytes()));
        return Hex.encodeHexString(sha256_HMAC.doFinal(str.getBytes("UTF-8"))).toUpperCase();
    }

    public void setupApi() throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        String timestamp = String.valueOf(new Date().getTime());

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://openapi.tuyaeu.com/v1.0/token?grant_type=1")
                .header("client_id", "uhc3xnmragt6r07yrbc4")
                .header("t", timestamp)
                .header("sign", calcSing("uhc3xnmragt6r07yrbc4","33d229ebad1743979ddf6253ce210be1", timestamp ))
                .header("sign_method", "HMAC-SHA256")
                .build(); // defaults to GET

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseBody responseBody = client.newCall(request).execute().body();
        entity = objectMapper.readValue(responseBody.string(), TokenRequestEntity.class);

        System.out.println(entity.getResult().getAccess_token());
    }

    public void refreshApiToken() throws NoSuchAlgorithmException, InvalidKeyException, IOException, NotAllowedException {
        OkHttpClient client = new OkHttpClient();
        String timestamp = String.valueOf(new Date().getTime());

        if(entity!=null){
            Request request = new Request.Builder()
                    .url("https://openapi.tuyaeu.com/v1.0/token/"+entity.getResult().getRefresh_token())
                    .header("client_id", "uhc3xnmragt6r07yrbc4")
                    .header("t", timestamp)
                    .header("sign", calcSing("uhc3xnmragt6r07yrbc4","33d229ebad1743979ddf6253ce210be1", timestamp ))
                    .header("sign_method", "HMAC-SHA256")
                    .build(); // defaults to GET

            ObjectMapper objectMapper = new ObjectMapper();
            ResponseBody responseBody = client.newCall(request).execute().body();
            entity = objectMapper.readValue(responseBody.string(), TokenRequestEntity.class);
        }
        else throw new NotAllowedException("Cannot refresh API token");
    }

    public void getDeviceInfo(String deviceId) throws NotAllowedException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        String timestamp = String.valueOf(new Date().getTime());
        OkHttpClient client = new OkHttpClient();

        if(entity!=null){
            System.out.println(entity.getResult().getAccess_token());
            Request request = new Request.Builder()
                    .url("https://openapi.tuyaeu.com/v1.0/devices/"+deviceId)
                    .header("client_id", "uhc3xnmragt6r07yrbc4")
                    .header("access_token", entity.getResult().getAccess_token())
                    .header("t", timestamp)
                    .header("sign", calcSing("uhc3xnmragt6r07yrbc4",entity.getResult().getAccess_token(),"33d229ebad1743979ddf6253ce210be1", timestamp ))
                    .header("sign_method", "HMAC-SHA256")
                    .build(); // defaults to GET

            ObjectMapper objectMapper = new ObjectMapper();
            ResponseBody responseBody = client.newCall(request).execute().body();

             mapperDevice = objectMapper.readValue(responseBody.string(), MapperDevice.class);
             device = mapperDevice.getResult();

        }else throw new NotAllowedException("Cannot get device data");

    }

    public void moveValve(String deviceId, String onOffValue) throws NoSuchAlgorithmException, InvalidKeyException, IOException, NotAllowedException {
        String json = "";
        if(device==null || entity==null){
            setupApi();
            getDeviceInfo(deviceId);
        }
        else refreshApiToken();

        if(device.getId().equals(deviceId)) {
            String timestamp = String.valueOf(new Date().getTime());
            OkHttpClient client = new OkHttpClient();

            if (getValveStatus(deviceId).equals("false")) {
                if (onOffValue.equals("true")) json = "{\"commands\":[{\"code\":\"switch_1\",\"value\":true}]}";
                else json = "{\"commands\":[{\"code\":\"switch_1\",\"value\":false}]}";
            } else {
                if (onOffValue.equals("false")) json = "{\"commands\":[{\"code\":\"switch_1\",\"value\":false}]}";
                else json = "{\"commands\":[{\"code\":\"switch_1\",\"value\":true}]}";
            }


            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), json);


            System.out.println(body.toString());

            Request request = new Request.Builder()
                    .url("https://openapi.tuyaeu.com/v1.0/devices/" + deviceId + "/commands")
                    .header("client_id", "uhc3xnmragt6r07yrbc4")
                    .header("access_token", entity.getResult().getAccess_token())
                    .header("t", timestamp)
                    .header("sign", calcSing("uhc3xnmragt6r07yrbc4",entity.getResult().getAccess_token(), "33d229ebad1743979ddf6253ce210be1", timestamp))
                    .header("sign_method", "HMAC-SHA256")
                    .header("Content-Type", "application/json")
                    .post(body)
                    .build();

            ObjectMapper objectMapper = new ObjectMapper();
            ResponseBody responseBody = client.newCall(request).execute().body();

            TypeReference<HashMap<String, String>> typeRef = new TypeReference<HashMap<String, String>>() {
            };
            HashMap<String, String> hashMap = objectMapper.readValue(responseBody.string(), typeRef);
            if (hashMap.get("success").equals("true")) {
                System.out.println("gut");
            }

        }else throw new NotAllowedException("Cannot turn on Valve");
    }

    public String getValveStatus(String deviceId) throws NotAllowedException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        if(device==null){
            setupApi();
            getDeviceInfo(deviceId);
        }
        if(device.getId().equals(deviceId)){
            for (DeviceStatus ds: device.getStatus()) {
                if(ds.getCode().equals("switch_1")){
                    return ds.getValue();
                }
            }
        }
        else throw new NotAllowedException("Cannot get status of Valve");
        return "Not Found";
    }

}
