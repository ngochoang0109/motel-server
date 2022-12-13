package com.server.kltn.motel.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.server.kltn.motel.api.user.payload.NewsFormPayload.AccomodationInfor;
import com.server.kltn.motel.api.user.payload.NewsFormPayload.CostCalculate;
import com.server.kltn.motel.api.user.payload.NewsFormPayload.DetailNews;
import com.server.kltn.motel.api.user.payload.NewsFormPayload.NewsInfor;

public interface PostNewsService {
	void createNews( NewsInfor newsInfor, AccomodationInfor accomodationInfor, 
					CostCalculate costCalculate, List<MultipartFile> images,String username);
	DetailNews getDetailNews(long postId);
	void extendedTimeToPost(long postId, CostCalculate costCalculate, String username);
}
