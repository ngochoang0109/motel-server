package com.server.kltn.motel.api.user.payload.paymentDetail;

public class OrderDetailPayload {
	private String image;
	private String title;
	private long orginalAmount;
	private long postAmount;
	private long discounted;
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getOrginalAmount() {
		return orginalAmount;
	}
	public void setOrginalAmount(long orginalAmount) {
		this.orginalAmount = orginalAmount;
	}
	public long getPostAmount() {
		return postAmount;
	}
	public void setPostAmount(long postAmount) {
		this.postAmount = postAmount;
	}
	public long getDiscounted() {
		return discounted;
	}
	public void setDiscounted(long discounted) {
		this.discounted = discounted;
	}
}
