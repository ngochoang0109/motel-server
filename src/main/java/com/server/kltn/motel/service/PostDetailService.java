package com.server.kltn.motel.service;

import java.util.List;

import com.server.kltn.motel.api.user.payload.PostDetailPayload;
import com.server.kltn.motel.api.user.payload.paymentDetail.CountRelatedNews;
import com.server.kltn.motel.api.user.payload.paymentDetail.HightExpenseRelated;
import com.server.kltn.motel.api.user.payload.paymentDetail.RelatedNews;

public interface PostDetailService {
	PostDetailPayload getPostDetail(long postId);
	List<CountRelatedNews> getRelatedNEws(String type, String district);
	List<HightExpenseRelated> getHightExpenseRelateds();
	List<RelatedNews> getRelatedNews(String province, String district);
}
