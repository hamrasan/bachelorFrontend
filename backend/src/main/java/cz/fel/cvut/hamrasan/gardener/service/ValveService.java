package cz.fel.cvut.hamrasan.gardener.service;

import org.apache.commons.codec.binary.Hex;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import com.google.common.hash.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;


import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class ValveService {
    private static final ObjectMapper mapper = new ObjectMapper();


    public ValveService() throws IOException, NoSuchAlgorithmException, InvalidKeyException {

    }

    public String calcSing(String cliendId, String secret, String timestamp) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        String str = cliendId + timestamp;
        System.out.println(timestamp);
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        byte[] hmacSha256 = null;
        hmacSha256 = sha256_HMAC.doFinal(str.getBytes());

        String hash = Base64.encodeBase64String(sha256_HMAC.doFinal(str.getBytes()));
        System.out.println(Hex.encodeHexString(sha256_HMAC.doFinal(str.getBytes("UTF-8"))).toUpperCase());
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

        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());


//
//        URL url = new URL("https://openapi.tuyaeu.com/v1.0/token?grant_type=1");
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        con.setRequestMethod("GET");
//
//        System.out.println( calcSing("uhc3xnmragt6r07yrbc4","33d229ebad1743979ddf6253ce210be1", timestamp ));
//
//        con.setRequestProperty("Content-Type", "application/json");
//        con.setRequestProperty("client_id", "uhc3xnmragt6r07yrbc4");
//        con.setRequestProperty("t", timestamp );
//        con.setRequestProperty("sign", calcSing("uhc3xnmragt6r07yrbc4","33d229ebad1743979ddf6253ce210be1", "1618167345185" ));
//        con.setRequestProperty("sign_method", "HMAC-SHA256");

//        con.setDoOutput(true);
//        DataOutputStream out = new DataOutputStream(con.getOutputStream());
////        out.writeBytes("token?grant_type=1");
//        out.flush();
//        out.close();
//
//        System.out.println(con.toString());
//
//        BufferedReader in = new BufferedReader(
//                new InputStreamReader(con.getInputStream()));
//        String inputLine;
//        StringBuffer content = new StringBuffer();
//        while ((inputLine = in.readLine()) != null) {
//            content.append(inputLine);
//        }
//        in.close();

//        System.out.println(content.toString());
    }

}
