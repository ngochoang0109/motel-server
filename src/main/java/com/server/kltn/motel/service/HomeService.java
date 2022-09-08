package com.server.kltn.motel.service;

import com.server.kltn.motel.api.user.payload.NewsCard;
import com.server.kltn.motel.page.Page;

public interface HomeService {
	Page<NewsCard> getPageNewsCard(int pageNo, int pageSize, String field, int mode);
}
