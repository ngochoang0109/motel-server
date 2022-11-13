package com.server.kltn.motel.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "accomodation")
public class Accomodation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String province;
	private String dicstrict;
	private String ward;
	private String street;
	private int area;
	private long price;
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
	
	@OneToOne(mappedBy = "accomodation",cascade = CascadeType.ALL)
    private Post post;
	
	@ManyToOne
    @JoinColumn(name="type")
    private TypeOfAcc typeOfAcc;
	
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
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getDicstrict() {
		return dicstrict;
	}
	public void setDicstrict(String dicstrict) {
		this.dicstrict = dicstrict;
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
	public Accomodation(String province, String dicstrict, String ward, String street, int area, long price,
			int numOfBed, int numOfToilet, int numOfFloor, int floorNumber, String furniture, boolean internet,
			boolean parking, boolean balcony, boolean airConditioner, boolean heater, String tower) {
		super();
		this.province = province;
		this.dicstrict = dicstrict;
		this.ward = ward;
		this.street = street;
		this.area = area;
		this.price = price;
		this.numOfBed = numOfBed;
		this.numOfToilet = numOfToilet;
		this.numOfFloor = numOfFloor;
		this.floorNumber = floorNumber;
		this.furniture = furniture;
		this.internet = internet;
		this.parking = parking;
		this.balcony = balcony;
		this.airConditioner = airConditioner;
		this.heater = heater;
		this.tower = tower;
	}
	
	public int getArea() {
		return area;
	}
	public void setArea(int area) {
		this.area = area;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
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
	public String getTower() {
		return tower;
	}
	public void setTower(String tower) {
		this.tower = tower;
	}
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	public TypeOfAcc getTypeOfAcc() {
		return typeOfAcc;
	}
	public void setTypeOfAcc(TypeOfAcc typeOfAcc) {
		this.typeOfAcc = typeOfAcc;
	}
	public Accomodation() {
		super();
	}
}
