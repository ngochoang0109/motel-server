package com.server.kltn.motel.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.server.kltn.motel.api.user.payload.AccomodationInfor;
import com.server.kltn.motel.api.user.payload.CostCalculate;
import com.server.kltn.motel.api.user.payload.NewsInfor;

public interface PostNewsService {
	void createNews( NewsInfor newsInfor, AccomodationInfor accomodationInfor, 
					CostCalculate costCalculate, List<MultipartFile> images,String username);
}
