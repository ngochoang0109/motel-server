package com.server.kltn.motel.service;

import com.server.kltn.motel.api.user.payload.CartPayload;

public interface CartService {
	void deletedCart(String username, long cartId, long newsId);
	CartPayload getCart(String username);
	CartPayload addNewsToCart(long idNews, String username);
}
