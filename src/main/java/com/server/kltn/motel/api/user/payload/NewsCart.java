package com.server.kltn.motel.api.user.payload;

import com.server.kltn.motel.api.admin.payload.DiscountDatasource;
import com.server.kltn.motel.api.admin.payload.ExpenseDatasource;
import com.server.kltn.motel.entity.TypeOfAcc;

public class NewsCart extends NewsCard{
	
	private ExpenseDatasource expenseDatasource;
	private DiscountDatasource discountDatasource;
	private TypeOfAcc typeOfAcc;
	private int numDate;
	private boolean checked;
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public int getNumDate() {
		return this.numDate;
	}

	public void setNumDate(int numDate) {
		this.numDate = numDate;
	}

	public TypeOfAcc getTypeOfAcc() {
		return this.typeOfAcc;
	}

	public void setTypeOfAcc(TypeOfAcc typeOfAcc) {
		this.typeOfAcc = typeOfAcc;
	}
	
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
				newsCard.getClosedDate(), newsCard.getTotalAmount());
	}
	public NewsCart() {
		super();
	}
}
