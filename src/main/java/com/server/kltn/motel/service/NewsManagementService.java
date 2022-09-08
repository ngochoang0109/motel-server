package com.server.kltn.motel.service;

import com.server.kltn.motel.api.user.payload.NewsCard;
import com.server.kltn.motel.page.Page;

public interface NewsManagementService {
	Page<NewsCard> getAllNewsOfUser(int pageNo, int pageSize, String field, int mode, String username);
	Page<NewsCard> getNewsWaitingAproved(int pageNo, int pageSize, String field, int mode, String username);
	Page<NewsCard> getNewsRejectOfUser(int pageNo, int pageSize, String field, int mode, String username);
	Page<NewsCard> getDontPaymentOfUser(int pageNo, int pageSize, String field, int mode, String username);
	Page<NewsCard> getWaittingShowOfUser(int pageNo, int pageSize, String field, int mode, String username);
	Page<NewsCard> getNewsShowingOfUser(int pageNo, int pageSize, String field, int mode, String username);
	Page<NewsCard> getNewsExpriedOfUser(int pageNo, int pageSize, String field, int mode, String username);
	Page<NewsCard> getNewsHiddenOfUser(int pageNo, int pageSize, String field, int mode, String username);
}
