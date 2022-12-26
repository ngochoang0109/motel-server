package com.server.kltn.motel.api.user.payload;

import java.time.LocalDateTime;

public class FilterParam {
	
	private int pageNo;
	private String textSearch;
	private LocalDateTime startedDate;
	private LocalDateTime closedDate;
	private Long typeOfAcc;
	private Long typeOfNews;
	private String province;
	private String district;
	
	public Long getTypeOfAcc() {
		return typeOfAcc;
	}
	public void setTypeOfAcc(Long typeOfAcc) {
		this.typeOfAcc = typeOfAcc;
	}
	public Long getTypeOfNews() {
		return typeOfNews;
	}
	public void setTypeOfNews(Long typeOfNews) {
		this.typeOfNews = typeOfNews;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public String getTextSearch() {
		return textSearch;
	}
	public void setTextSearch(String textSearch) {
		this.textSearch = textSearch;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public LocalDateTime getStartedDate() {
		return startedDate;
	}
	public void setStartedDate(LocalDateTime startedDate) {
		this.startedDate = startedDate;
	}
	public LocalDateTime getClosedDate() {
		return closedDate;
	}
	public void setClosedDate(LocalDateTime closedDate) {
		this.closedDate = closedDate;
	}
}
