package com.server.kltn.motel.api.user.payload;

public class UserInfor {
	private long id;
	private String fullname;
	private String phone;
	private String address;
	private String email;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public UserInfor(String fullname, String phone, String address, String email) {
		super();
		this.fullname = fullname;
		this.phone = phone;
		this.address = address;
		this.email = email;
	}
}
