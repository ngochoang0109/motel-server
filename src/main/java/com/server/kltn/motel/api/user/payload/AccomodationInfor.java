package com.server.kltn.motel.api.user.payload;

public class AccomodationInfor {
	private long typesOfAcc;
	private boolean airConditioner;
	private boolean parking;
	private boolean internet;
	private boolean heater;
	private boolean	balcony;
	private String province;
	private String	district;
	private String ward;
	private String street;
	private String tower;
	private int area;
	private int floorNumber;
	private int numOfBed;
	private int numOfFloor;
	private int numOfToilet;
	private String	furniture;
	private String	directionBalcony;
	private String	directionHouse;
	private long price;
	public long getTypesOfAcc() {
		return typesOfAcc;
	}
	public void setTypesOfAcc(long typesOfAcc) {
		this.typesOfAcc = typesOfAcc;
	}
	public boolean isAirConditioner() {
		return airConditioner;
	}
	public void setAirConditioner(boolean airConditioner) {
		this.airConditioner = airConditioner;
	}
	public boolean isParking() {
		return parking;
	}
	public void setParking(boolean parking) {
		this.parking = parking;
	}
	public boolean isInternet() {
		return internet;
	}
	public void setInternet(boolean internet) {
		this.internet = internet;
	}
	public boolean isHeater() {
		return heater;
	}
	public void setHeater(boolean heater) {
		this.heater = heater;
	}
	public boolean isBalcony() {
		return balcony;
	}
	public void setBalcony(boolean balcony) {
		this.balcony = balcony;
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
	public String getTower() {
		return tower;
	}
	public void setTower(String tower) {
		this.tower = tower;
	}
	public int getArea() {
		return area;
	}
	public void setArea(int area) {
		this.area = area;
	}
	public int getFloorNumber() {
		return floorNumber;
	}
	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}
	public int getNumOfBed() {
		return numOfBed;
	}
	public void setNumOfBed(int numOfBed) {
		this.numOfBed = numOfBed;
	}
	public int getNumOfFloor() {
		return numOfFloor;
	}
	public void setNumOfFloor(int numOfFloor) {
		this.numOfFloor = numOfFloor;
	}
	public int getNumOfToilet() {
		return numOfToilet;
	}
	public void setNumOfToilet(int numOfToilet) {
		this.numOfToilet = numOfToilet;
	}
	public String getFurniture() {
		return furniture;
	}
	public void setFurniture(String furniture) {
		this.furniture = furniture;
	}
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
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
}
