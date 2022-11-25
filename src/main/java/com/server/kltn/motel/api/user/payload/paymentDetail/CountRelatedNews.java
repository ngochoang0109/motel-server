package com.server.kltn.motel.api.user.payload.paymentDetail;

public class CountRelatedNews {
	
	private long numNews;
	private String ward;
	
	public long getNumNews() {
		return numNews;
	}
	public void setNumNews(long numNews) {
		this.numNews = numNews;
	}
	public String getWard() {
		return ward;
	}
	public void setWard(String ward) {
		this.ward = ward;
	}
	public CountRelatedNews(long numNews, String ward) {
		super();
		this.numNews = numNews;
		this.ward = ward;
	}
}
