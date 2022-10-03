package com.server.kltn.motel.service;

import java.util.List;

import com.server.kltn.motel.api.user.payload.NewsCard;
import com.server.kltn.motel.api.user.payload.SearchParam;
import com.server.kltn.motel.entity.Post;
import com.server.kltn.motel.page.Page;

public interface HomeService {
	Page<NewsCard> getPageNewsCard(int pageNo, int pageSize, String field, int mode);
	Page<NewsCard> getPageNewsCardByType(int pageNo, int pageSize, String field, int mode,long type);
	List<Post> sortPriorityAndDate(List<Post> posts);
	Page<NewsCard> searchPageCard(int pageNo, int pageSize, String field, int mode,SearchParam searchParam);
}
