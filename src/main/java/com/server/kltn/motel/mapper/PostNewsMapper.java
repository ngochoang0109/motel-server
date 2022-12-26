package com.server.kltn.motel.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.server.kltn.motel.api.user.payload.NewsFormPayload.AccomodationInfor;
import com.server.kltn.motel.api.user.payload.NewsFormPayload.NewsInfor;
import com.server.kltn.motel.entity.Accomodation;
import com.server.kltn.motel.entity.Image;
import com.server.kltn.motel.entity.Post;

@Component
public class PostNewsMapper {
	public Post mapNewsInforToPost(NewsInfor newsInfor) {
		Post post = new Post();
		post.setTitle(newsInfor.getTitle());
		post.setDescription(newsInfor.getDescription());
		post.setTotalAmount(newsInfor.getTotalAmount());
		return post;
	}

	public Accomodation mapAccomodationInforToAccomodation(AccomodationInfor accomodationInfor) {
		Accomodation accomodation = new Accomodation(accomodationInfor.getProvince(), accomodationInfor.getDistrict(),
				accomodationInfor.getWard(), accomodationInfor.getStreet(), accomodationInfor.getArea(),
				accomodationInfor.getPrice(), accomodationInfor.getNumOfBed(), accomodationInfor.getNumOfToilet(),
				accomodationInfor.getNumOfFloor(), accomodationInfor.getFloorNumber(), accomodationInfor.getFurniture(),
				accomodationInfor.isInternet(), accomodationInfor.isParking(), accomodationInfor.isBalcony(),
				accomodationInfor.isAirConditioner(), accomodationInfor.isHeater(), accomodationInfor.getTower(), 
				accomodationInfor.getDirectionBalcony(), accomodationInfor.getDirectionHouse());
		return accomodation;
	}
	
	public List<Image> convertUrlImagesToImages(List<String> urlImages) {
		List<Image> images= new ArrayList<>();
		
		images.add(new Image(urlImages.get(urlImages.size()-1),"avatar"));
		
		for(int i=0;i<urlImages.size()-1;i++) {
			images.add(new Image(urlImages.get(i),"image"));
		}
		return images;
	}
}