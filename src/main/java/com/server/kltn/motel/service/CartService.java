package com.server.kltn.motel.service;

import java.util.List;

import com.server.kltn.motel.api.user.payload.CartPayload;
import com.server.kltn.motel.api.user.payload.UpdateItemCart;

public interface CartService {
	void deletedCart(String username, long cartId, long newsId);
	CartPayload getCart(String username);
	CartPayload addNewsToCart(long idNews, String username);
	CartPayload updateItem(String username, UpdateItemCart updateItemCart,long cartId, long newsId);
	CartPayload updateItem(String username, List<UpdateItemCart> updateItemCart,long cartId);
}
