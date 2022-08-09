package com.server.kltn.motel.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "accomodation")
public class Accomodation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String address;
	private String area;
	private String price;
	private int numOfBed;
	private int numOfToilet;
	private int numOfFloor;
	private int floorNumber;
	private String furniture;
	private boolean internet;
	private boolean parking;
	private String tower;
	@OneToOne(mappedBy = "expense")
    private Post post;
	@OneToOne
    @JoinColumn(name="type")
    private TypeOfAcc typeOfAcc;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
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
	
}
