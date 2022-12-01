package com.server.kltn.motel.api.user.payload.HomePayload;

import java.util.List;

public class SearchExpand {
	
	private List<Integer> numBeds;
	private String directionHouse;
	/*1: image, 2:video*/
	private int media;
	
	public List<Integer> getNumBeds() {
		return numBeds;
	}
	public void setNumBeds(List<Integer> numBeds) {
		this.numBeds = numBeds;
	}
	public String getDirectionHouse() {
		return directionHouse;
	}
	public void setDirectionHouse(String directionHouse) {
		this.directionHouse = directionHouse;
	}
	public int getMedia() {
		return media;
	}
	public void setMedia(int media) {
		this.media = media;
	}
}
