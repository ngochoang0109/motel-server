package com.server.kltn.motel.api.user.payload;

public class UserInfor {
	private long id;
	private String fullname;
	private String phone;
	private String address;
	private String email;
	private String username;
	private String avatar;
	
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
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
	public UserInfor(String fullname, String phone, String address, String email, String username,
			String avatar) {
		super();
		this.fullname = fullname;
		this.phone = phone;
		this.address = address;
		this.email = email;
		this.username = username;
		this.avatar = avatar;
	}
}
