package com.server.kltn.motel.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.server.kltn.motel.constant.DateTimeConstant;

@Component
public class HandleDateCommon {
	public LocalDateTime getCurrentDateTime() {
		LocalDateTime currentTime= LocalDateTime.now();
		LocalDateTime returnValueDateTime= currentTime.plusHours(DateTimeConstant.UtcTimeZoneVN);
		return returnValueDateTime;
	}
	
	public LocalDateTime convertStringDateToLocalDateTime(String strDate) {
		if (strDate == null) {
			return null;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
		//Parse String to LocalDateTime
		LocalDateTime dateTime = LocalDateTime.parse(strDate, formatter);
		return dateTime;
	}
}
