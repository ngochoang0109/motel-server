package com.server.kltn.motel.common;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class VnpayCommon {
	private final String vnpVersion="2.1.0";
	private final String vnpTmnCode="EF9MJL2L";
	private final String vnpBankCode="vnp_BankCode=VNBANK";
	private final String vnpCurrCode="VND";
	private final String vnpLocale="vn";
	private final String vnpReturnUrl="http://localhost:8080/payment/result";
	private final String vnpHashSecret="ZRZPTEOVVAJTIUBWMQICUPBRBFTJVDQA";
	public static String vnpCommand = "pay";
	
	public String getRandomNumber(int len) {
        Random rnd = new Random();
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }
	
	public String getIpAddress(HttpServletRequest request) {
        String ipAdress="";
        try {
            ipAdress = request.getHeader("X-FORWARDED-FOR");
            if (ipAdress == null) {
                ipAdress = request.getRemoteAddr();
            }
        } catch (Exception e) {
            ipAdress = "Invalid IP:" + e.getMessage();
        }
        return ipAdress;
    }
}
