package com.server.kltn.motel.api.user.payload;

public class CostCalculate {
	private long typeOfPost;
	private long numDatePos;
	private String startedDate;
	private String discount;
	public long getTypeOfPost() {
		return typeOfPost;
	}
	public void setTypeOfPost(long typeOfPost) {
		this.typeOfPost = typeOfPost;
	}
	public long getNumDatePos() {
		return numDatePos;
	}
	public void setNumDatePos(long numDatePos) {
		this.numDatePos = numDatePos;
	}
	public String getStartedDate() {
		return startedDate;
	}
	public void setStartedDate(String startedDate) {
		this.startedDate = startedDate;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
}