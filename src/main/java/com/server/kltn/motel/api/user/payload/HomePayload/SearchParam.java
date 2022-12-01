package com.server.kltn.motel.api.user.payload.HomePayload;

public class SearchParam {
	private long type;
	private String province;
	private String district;
	private String ward;
	private long priceFrom;
	private long priceTo;
	private int areaFrom;
	private int areaTo;

	
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
