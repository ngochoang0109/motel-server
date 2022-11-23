package com.server.kltn.motel.api.user.payload;
import java.util.List;

public class PostDetailPayload {
	private long postId;
	private List<String> images;
	private List<String> videos;
	private String province;
	private String district;
	private String ward;
	private String street;
	private String title;
	private long price;
	private int area;
	private int numOfBed;
	private int numOfToilet;
	private int numOfFloor;
	private int floorNumber;
	private String furniture;
	private boolean internet;
	private boolean parking;
	private boolean balcony;
	private boolean airConditioner;
	private boolean heater;
	private String tower;
	private String description;
	private String typeOfPost;
	private String startedDate;
	private String closedDate;
	private String typeExpense;
	private String fullname;
	private long otherPost;
	private String phone;
	private String avatar;
	private String directionBalcony;
	private String directionHouse;
	
	public String getDirectionBalcony() {
		return directionBalcony;
	}
	public void setDirectionBalcony(String directionBalcony) {
		this.directionBalcony = directionBalcony;
	}
	public String getDirectionHouse() {
		return directionHouse;
	}
	public void setDirectionHouse(String directionHouse) {
		this.directionHouse = directionHouse;
	}
	public long getPostId() {
		return postId;
	}
	public void setPostId(long postId) {
		this.postId = postId;
	}
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
	public List<String> getVideos() {
		return videos;
	}
	public void setVideos(List<String> videos) {
		this.videos = videos;
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
	public String getWard() {
		return ward;
	}
	public void setWard(String ward) {
		this.ward = ward;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public int getNumOfBed() {
		return numOfBed;
	}
	public void setNumOfBed(int numOfBed) {
		this.numOfBed = numOfBed;
	}
	public int getNumOfToilet() {
		return numOfToilet;
	}
	public void setNumOfToilet(int numOfToilet) {
		this.numOfToilet = numOfToilet;
	}
	public int getNumOfFloor() {
		return numOfFloor;
	}
	public void setNumOfFloor(int numOfFloor) {
		this.numOfFloor = numOfFloor;
	}
	public int getFloorNumber() {
		return floorNumber;
	}
	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}
	public String getFurniture() {
		return furniture;
	}
	public void setFurniture(String furniture) {
		this.furniture = furniture;
	}
	public boolean isInternet() {
		return internet;
	}
	public void setInternet(boolean internet) {
		this.internet = internet;
	}
	public boolean isParking() {
		return parking;
	}
	public void setParking(boolean parking) {
		this.parking = parking;
	}
	public boolean isBalcony() {
		return balcony;
	}
	public void setBalcony(boolean balcony) {
		this.balcony = balcony;
	}
	public boolean isAirConditioner() {
		return airConditioner;
	}
	public void setAirConditioner(boolean airConditioner) {
		this.airConditioner = airConditioner;
	}
	public boolean isHeater() {
		return heater;
	}
	public void setHeater(boolean heater) {
		this.heater = heater;
	}
	public String getTower() {
		return tower;
	}
	public void setTower(String tower) {
		this.tower = tower;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTypeOfPost() {
		return typeOfPost;
	}
	public void setTypeOfPost(String typeOfPost) {
		this.typeOfPost = typeOfPost;
	}
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
	public String getTypeExpense() {
		return typeExpense;
	}
	public void setTypeExpense(String typeExpense) {
		this.typeExpense = typeExpense;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public long getOtherPost() {
		return otherPost;
	}
	public void setOtherPost(long otherPost) {
		this.otherPost = otherPost;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
}
