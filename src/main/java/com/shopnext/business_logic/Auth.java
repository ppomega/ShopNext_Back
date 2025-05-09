package com.shopnext.business_logic;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shopnext.models.User;
import com.shopnext.repos.UserRepo;

@Service
public class Auth {
    @Autowired
    UserRepo userRepo;

    @Value("${jwt.secret}")
    String secretKey;

    static HashMap<String, String> map;

    public static String calculateHMAC(String secretKey, String message) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKeySpec);
            byte[] hmacBytes = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeBase64URLSafeString(hmacBytes); // Use Base64 for easier handling
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Error calculating HMAC", e);
        }
    }

    public boolean isCorrect(String token) throws Exception {
        String headers = "{\"alg\":\"HS256\"}";
        String payload = "{\"name\":\"" + map.get("name") + "\",\"password\":\"" + map.get("password") + "\"}";
        String headers64 = Base64.encodeBase64String(headers.getBytes());
        String payload64 = Base64.encodeBase64String(payload.getBytes());
        String sec = this.secretKey;
        String sign = calculateHMAC(sec, headers64 + "." + payload64);
        String token1 = headers64 + "." + payload64 + "." + sign;
        if (token1.equals(token)) {
            return true;
        }
        return false;
    }

    public User getUser(String token) throws Exception {
        map = getUserInfo(token);
        if (!isCorrect(token)) {
            return null;
        }
        User user = userRepo.findByName(map.get("name"));
        if (user == null) {
            return null;
        }
        if (map.get("password").equals(user.password)) {
            return user;
        } else {
            return null;
        }
    }

    public HashMap<String, String> sTojS(String payload) throws Exception {
        payload = payload.substring(1, payload.length() - 1);
        HashMap<String, String> map = new HashMap<>();
        String[] parts = payload.split(",");
        for (String part : parts) {
            String[] keyValue = part.split(":");
            String key = keyValue[0].replace("\"", "");
            String value = keyValue[1].replace("\"", "");
            map.put(key, value);
        }
        return map;
    }

    public HashMap<String, String> getUserInfo(String token) throws Exception {
        String[] parts = token.split("\\.");
        String payload = new String(Base64.decodeBase64(parts[1]), StandardCharsets.UTF_8);

        return sTojS(payload);
    }

}
