package com.server.kltn.motel.service;

import java.util.List;

import com.server.kltn.motel.api.user.payload.NewsCard;
import com.server.kltn.motel.api.user.payload.HomePayload.CountNews;
import com.server.kltn.motel.api.user.payload.HomePayload.SearchParam;
import com.server.kltn.motel.page.Page;

public interface HomeService {
	Page<NewsCard> searchPageCard(int pageNo, int pageSize, String field, int mode, 
									SearchParam searchParam);
	List<CountNews> getCountByProvince();
}
