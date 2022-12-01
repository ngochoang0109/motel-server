package com.server.kltn.motel.api.user.payload.HomePayload;

public class CountNews {
	private String province;
	private long count;
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public CountNews( long count,String province) {
		super();
		this.province = province;
		this.count = count;
	}
}