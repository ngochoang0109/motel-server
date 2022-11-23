package com.server.kltn.motel.service;

import com.server.kltn.motel.api.user.payload.PostDetailPayload;

public interface PostDetailService {
	PostDetailPayload getPostDetail(long postId);
}
