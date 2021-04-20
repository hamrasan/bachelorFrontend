package cz.fel.cvut.hamrasan.gardener.service;

import com.fasterxml.jackson.core.type.TypeReference;
import cz.fel.cvut.hamrasan.gardener.dao.GardenDao;
import cz.fel.cvut.hamrasan.gardener.dao.UserDao;
import cz.fel.cvut.hamrasan.gardener.dao.ValveDao;
import cz.fel.cvut.hamrasan.gardener.dto.ValveDto;
import cz.fel.cvut.hamrasan.gardener.dto.ValveWithScheduleDto;
import cz.fel.cvut.hamrasan.gardener.exceptions.AlreadyExistsException;
import cz.fel.cvut.hamrasan.gardener.exceptions.NotAllowedException;
import cz.fel.cvut.hamrasan.gardener.exceptions.NotFoundException;
import cz.fel.cvut.hamrasan.gardener.model.*;
import cz.fel.cvut.hamrasan.gardener.security.SecurityUtils;
import okhttp3.*;
import org.apache.commons.codec.binary.Hex;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.io.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ValveService {
    private static final ObjectMapper mapper = new ObjectMapper();
    private TokenRequestEntity entity = null;
    private Device device = null;
    private MapperDevice mapperDevice = null;

    private ValveDao valveDao;
    private UserDao userDao;
    private TranslateService translateService;
    private GardenDao gardenDao;

    @Autowired
    public ValveService(ValveDao valveDao, UserDao userDao, TranslateService translateService, GardenDao gardenDao) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        this.valveDao = valveDao;
        this.userDao = userDao;
        this.translateService = translateService;
        this.gardenDao = gardenDao;
    }

    @Transactional
    public List<ValveDto> getAllOfUserRaw() throws NotFoundException {
        User user = userDao.find( SecurityUtils.getCurrentUser().getId());
        List<ValveDto> valveDtos = new ArrayList<ValveDto>();

        for (Valve valve: user.getValves()) {
            valveDtos.add(translateService.translateValve(valve));
        }

        return valveDtos;
    }

    @Transactional
    public List<ValveWithScheduleDto> getAllOfUserFull() throws NotFoundException {
        User user = userDao.find( SecurityUtils.getCurrentUser().getId());
        List<ValveWithScheduleDto> ValveWithScheduleDto = new ArrayList<ValveWithScheduleDto>();

        for (Valve valve: user.getValves()) {
            ValveWithScheduleDto.add(translateService.translateValveWithSchedule(valve));
        }
        return ValveWithScheduleDto;
    }

    @Transactional
    public void createValve(String valveName) throws NoSuchAlgorithmException, InvalidKeyException, IOException, NotAllowedException, AlreadyExistsException {
        if(entity == null) setupApi();
        if(device == null) getDeviceInfo(valveName);

        if(valveDao.findByName(valveName) != null) throw new AlreadyExistsException();

        Valve valve = new Valve(valveName, ("https://images.tuyacn.com/"+device.getIcon()),userDao.find( SecurityUtils.getCurrentUser().getId()));
        valveDao.persist(valve);
    }

    @Transactional
    public void valvingImmediately(String valveName, Integer length) throws NoSuchAlgorithmException, InvalidKeyException, IOException, NotAllowedException, NotFoundException {
        if(entity == null) setupApi();
        else refreshApiToken();
        if(device == null) getDeviceInfo(valveName);

        moveValve(valveName, "true");
        setStopValving(valveName, length );
    }

    @Transactional
    public void updateGardensToValve(Long id, List<Long> gardensId){
        Valve valve = valveDao.find(id);
        User user = SecurityUtils.getCurrentUser();

        for (int i = 0; i < valve.getGardens().size() ; i++) {
            if(!gardensId.contains( valve.getGardens().get(i))) {
                valve.removeGarden( valve.getGardens().get(i));
            }
        }

        for (long i : gardensId) {
            Garden garden = gardenDao.find(i);
            if(!valve.getGardens().contains(garden)){
                valve.addGarden(garden);
            }
        }
        valveDao.update(valve);
    }


    private String calcSing(String cliendId, String secret, String timestamp) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        String str = cliendId + timestamp;
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        byte[] hmacSha256 = null;
        hmacSha256 = sha256_HMAC.doFinal(str.getBytes());

        String hash = Base64.encodeBase64String(sha256_HMAC.doFinal(str.getBytes()));
        return Hex.encodeHexString(sha256_HMAC.doFinal(str.getBytes("UTF-8"))).toUpperCase();
    }

    private String calcSing(String cliendId, String accessToken, String secret, String timestamp) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
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
        if( entity==null){
            setupApi();
        }
        else refreshApiToken();

        getDeviceInfo(deviceId);

        if(device.getId().equals(deviceId)) {

            if (getValveStatus(deviceId).equals("false")) {
                if (onOffValue.equals("true")) json = "{\"commands\":[{\"code\":\"switch_1\",\"value\":true}]}";
                else json = "{\"commands\":[{\"code\":\"switch_1\",\"value\":false}]}";
            } else {
                if (onOffValue.equals("false")) json = "{\"commands\":[{\"code\":\"switch_1\",\"value\":false}]}";
                else json = "{\"commands\":[{\"code\":\"switch_1\",\"value\":true}]}";
            }

            sendCommandRequest(deviceId, json);

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


    public void setStopValving(String deviceId, Integer length) throws NoSuchAlgorithmException, InvalidKeyException, IOException, NotAllowedException, NotFoundException {
        String json = "";
        Integer seconds = length*60;

        if( entity==null){
            setupApi();
        }
        else refreshApiToken();

        getDeviceInfo(deviceId);

        if (device.getId().equals(deviceId)) {

            json = "{\"commands\":[{\"code\":\"countdown_1\",\"value\":"+ seconds +"}]}";
            sendCommandRequest(deviceId, json);
        }else throw new NotFoundException("Valve not found");

    }


    private void sendCommandRequest(String deviceId, String json) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        String timestamp = String.valueOf(new Date().getTime());
        OkHttpClient client = new OkHttpClient();

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
    }


}
