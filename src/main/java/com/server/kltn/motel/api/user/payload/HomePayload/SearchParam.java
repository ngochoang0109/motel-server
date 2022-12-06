package com.server.kltn.motel.api.user.payload.HomePayload;

import java.util.List;

public class SearchParam {
	private String textSearch;
	private long type;
	private String province;
	private String district;
	private String ward;
	private long priceFrom;
	private long priceTo;
	private int areaFrom;
	private int areaTo;
	private List<Integer> numBeds;
	private List<String> directionHouse;
	/*1: image, 2:video*/
	private List<Integer> media;
	
	public String getTextSearch() {
		return textSearch;
	}
	public void setTextSearch(String textSearch) {
		this.textSearch = textSearch;
	}
	public List<String> getDirectionHouse() {
		return directionHouse;
	}
	public void setDirectionHouse(List<String> directionHouse) {
		this.directionHouse = directionHouse;
	}
	public List<Integer> getMedia() {
		return media;
	}
	public void setMedia(List<Integer> media) {
		this.media = media;
	}
	public List<Integer> getNumBeds() {
		return numBeds;
	}
	public void setNumBeds(List<Integer> numBeds) {
		this.numBeds = numBeds;
	}
	
	public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
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

	public long getPriceFrom() {
		return priceFrom;
	}

	public void setPriceFrom(long priceFrom) {
		this.priceFrom = priceFrom;
	}

	public long getPriceTo() {
		return priceTo;
	}

	public void setPriceTo(long priceTo) {
		this.priceTo = priceTo;
	}

	public int getAreaFrom() {
		return areaFrom;
	}

	public void setAreaFrom(int areaFrom) {
		this.areaFrom = areaFrom;
	}

	public int getAreaTo() {
		return areaTo;
	}

	public void setAreaTo(int areaTo) {
		this.areaTo = areaTo;
	}

	public long getType() {
		return type;
	}

	public void setType(long type) {
		this.type = type;
	}
}
