package com.server.kltn.motel.common;

import java.time.LocalDateTime;

import com.server.kltn.motel.constant.DateTimeConstant;

public class HandleDateCommon {
	public LocalDateTime getCurrentDateTime() {
		LocalDateTime currentTime= LocalDateTime.now();
		LocalDateTime returnValueDateTime= currentTime.plusHours(DateTimeConstant.UtcTimeZoneVN);
		return returnValueDateTime;
	}
	
	
}
