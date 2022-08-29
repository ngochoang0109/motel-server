package com.server.kltn.motel.common;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.server.kltn.motel.constant.DateTimeConstant;

public class HandleDateCommon {
	public LocalDateTime getCurrentDateTime() {
		LocalDateTime currentTime= LocalDateTime.now();
		LocalDateTime returnValueDateTime= currentTime.plusHours(DateTimeConstant.UtcTimeZoneVN);
		return returnValueDateTime;
	}
	
	public LocalDateTime convertStringDateToLocalDateTime(String strDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
		//Parse String to LocalDateTime
		LocalDateTime dateTime = LocalDateTime.parse(strDate, formatter);
		return dateTime;
	}
}
