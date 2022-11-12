package com.server.kltn.motel.api.user.payload;

import java.util.ArrayList;
import java.util.List;

public class CartPayload {
	private long idCart;
	List<NewsCart> newsCarts= new ArrayList<>();
	private long totalPriceOfCart;
	
	public long getTotalPriceOfCart() {
		return totalPriceOfCart;
	}
	public void setTotalPriceOfCart(long totalPriceOfCart) {
		this.totalPriceOfCart = totalPriceOfCart;
	}
	public long getIdCart() {
		return idCart;
	}
	public void setIdCart(long idCart) {
		this.idCart = idCart;
	}
	public List<NewsCart> getNewsCarts() {
		return newsCarts;
	}
	public void setNewsCarts(List<NewsCart> newsCarts) {
		this.newsCarts = newsCarts;
	}
}
