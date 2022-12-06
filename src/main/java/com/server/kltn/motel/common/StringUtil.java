package com.server.kltn.motel.common;

import java.text.Normalizer;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class StringUtil {
	public String standardizedString(String initial){
		String temp = Normalizer.normalize(initial, Normalizer.Form.NFD); 
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+"); 
		temp = pattern.matcher(temp).replaceAll(""); 
		return temp.trim().toLowerCase();
	}
}
