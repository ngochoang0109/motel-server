package com.server.kltn.motel.service;

import java.util.List;

import com.server.kltn.motel.api.admin.payload.RejectDatasource;
import com.server.kltn.motel.api.user.payload.Cart;
import com.server.kltn.motel.api.user.payload.FilterParam;
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
	Page<NewsCard> getNewsByTextSearch(int pageNo, int pageSize, String field, int mode,
										String username, String status,String textSearch, FilterParam filterForm);
	List<NewsCard> getNewsByStatus(String mode);
	Boolean approvedNews(long id);
	Boolean rejectedNews(long id);
	Boolean insertReason(RejectDatasource rejectDatasource);
	String selReason(long id);
	Cart addNewsToCart(long idNews, String username);
	Cart getCart(String username);
}
