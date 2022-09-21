package com.server.kltn.motel.api.user.payload;

public class FilterParam {
	private String startedDate;
	private String closedDate;
	private long[] typeOfAcc;
	private long[] typeOfNews;
	private String[] province;
	private String[] district;
	
	public String getStartedDate() {
		return startedDate;
	}
	public void setStartedDate(String startedDate) {
		this.startedDate = startedDate;
	}
	public String getClosedDate() {
		return closedDate;
	}
	public void setClosedDate(String closedDate) {
		this.closedDate = closedDate;
	}
	public long[] getTypeOfAcc() {
		return typeOfAcc;
	}
	public void setTypeOfAcc(long[] typeOfAcc) {
		this.typeOfAcc = typeOfAcc;
	}
	public long[] getTypeOfNews() {
		return typeOfNews;
	}
	public void setTypeOfNews(long[] typeOfNews) {
		this.typeOfNews = typeOfNews;
	}
	public String[] getProvince() {
		return province;
	}
	public void setProvince(String[] province) {
		this.province = province;
	}
	public String[] getDistrict() {
		return district;
	}
	public void setDistrict(String[] district) {
		this.district = district;
	}
	
}
