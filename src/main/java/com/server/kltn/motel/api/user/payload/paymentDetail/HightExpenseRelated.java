package com.server.kltn.motel.api.user.payload.paymentDetail;

public class HightExpenseRelated {
	
	private String typePost;
	private String wardOrStreet;
	
	public String getTypePost() {
		return typePost;
	}
	public void setTypePost(String typePost) {
		this.typePost = typePost;
	}
	public String getWardOrStreet() {
		return wardOrStreet;
	}
	public void setWardOrStreet(String wardOrStreet) {
		this.wardOrStreet = wardOrStreet;
	}
	public HightExpenseRelated(String typePost, String wardOrStreet) {
		super();
		this.typePost = typePost;
		this.wardOrStreet = wardOrStreet;
	}
}
