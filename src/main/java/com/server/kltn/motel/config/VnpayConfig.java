package com.server.kltn.motel.config;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

@Component
public class VnpayConfig {
	
	public final String vnp_TmnCode = "EF9MJL2L";
	public final String vnp_HashSecret = "ZRZPTEOVVAJTIUBWMQICUPBRBFTJVDQA";
	public final String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
	
	public String getRandomNumber(int len) {
        Random rnd = new Random();
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }
	
	public String hmacWithJava(String algorithm, String data, String key)
            throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
        Mac mac = Mac.getInstance(algorithm);
        mac.init(secretKeySpec);
        return bytesToHex(mac.doFinal(data.getBytes()));
    }
	
	public static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte h : hash) {
            String hex = Integer.toHexString(0xff & h);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
	
	public String hmacSHA512(final String key, final String data) throws NoSuchAlgorithmException, InvalidKeyException {
        String hmacSHA512Algorithm = "HmacSHA512";
        return this.hmacWithJava(hmacSHA512Algorithm, data, key);
    }
}
