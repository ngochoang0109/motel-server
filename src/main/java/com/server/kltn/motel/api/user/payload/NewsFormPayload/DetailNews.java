package com.server.kltn.motel.api.user.payload.NewsFormPayload;

import java.util.LinkedList;
import java.util.List;

public class DetailNews {
	private AccomodationInfor accomodationInfor;
	private NewsInfor newsInfor;
	private List<String> images = new LinkedList<>();
	
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
	public AccomodationInfor getAccomodationInfor() {
		return accomodationInfor;
	}
	public void setAccomodationInfor(AccomodationInfor accomodationInfor) {
		this.accomodationInfor = accomodationInfor;
	}
	public NewsInfor getNewsInfor() {
		return newsInfor;
	}
	public void setNewsInfor(NewsInfor newsInfor) {
		this.newsInfor = newsInfor;
	}
}
