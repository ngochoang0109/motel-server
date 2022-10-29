package com.server.kltn.motel.api.user.payload;

public class NewsCard {
	private long id;
	private String phone;
	private String fullName;
	private String title;
	private String description;
	private String avatar;
	private long price;
	private int area;
	private String district;
	private String province;
	private String startedDate;
	private String closedDate;
	private String mode;
	private long totalAmount;
	
	public long getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getClosedDate() {
		return closedDate;
	}
	public void setClosedDate(String closedDate) {
		this.closedDate = closedDate;
	}
	public String getStartedDate() {
		return startedDate;
	}
	public void setStartedDate(String startedDate) {
		this.startedDate = startedDate;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public int getArea() {
		return area;
	}
	public void setArea(int area) {
		this.area = area;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public NewsCard(long id, String phone, String fullName, String title, String description, String avatar, long price,
			int area, String district, String province, String startedDate, String closedDate) {
		super();
		this.id= id;
		this.phone = phone;
		this.fullName = fullName;
		this.title = title;
		this.description = description;
		this.avatar = avatar;
		this.price = price;
		this.area = area;
		this.district = district;
		this.province = province;
		this.startedDate=startedDate;
		this.closedDate=closedDate;
	}
	public NewsCard() {
		super();
	}
}
