package com.server.kltn.motel.api.user.payload;

import com.server.kltn.motel.api.admin.payload.DiscountDatasource;
import com.server.kltn.motel.api.admin.payload.ExpenseDatasource;

public class NewsCart extends NewsCard{
	
	private ExpenseDatasource expenseDatasource;
	private DiscountDatasource discountDatasource;
	
	public ExpenseDatasource getExpenseDatasource() {
		return expenseDatasource;
	}
	public void setExpenseDatasource(ExpenseDatasource expenseDatasource) {
		this.expenseDatasource = expenseDatasource;
	}
	public DiscountDatasource getDiscountDatasource() {
		return discountDatasource;
	}
	public void setDiscountDatasource(DiscountDatasource discountDatasource) {
		this.discountDatasource = discountDatasource;
	}
	public NewsCart(NewsCard newsCard) {
		super(newsCard.getId(), newsCard.getPhone(), newsCard.getFullName(), 
				newsCard.getTitle(), newsCard.getDescription(), newsCard.getAvatar(),newsCard.getPrice(),
				newsCard.getArea(), newsCard.getDistrict(), newsCard.getProvince(),newsCard.getStartedDate(), 
				newsCard.getClosedDate());
	}
	public NewsCart() {
		super();
	}
}
