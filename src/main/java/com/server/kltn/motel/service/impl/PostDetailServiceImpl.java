package com.server.kltn.motel.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.kltn.motel.api.user.payload.PostDetailPayload;
import com.server.kltn.motel.entity.Image;
import com.server.kltn.motel.entity.Post;
import com.server.kltn.motel.entity.Video;
import com.server.kltn.motel.repository.PostRepository;
import com.server.kltn.motel.service.PostDetailService;

@Service
public class PostDetailServiceImpl implements PostDetailService{
	
	@Autowired
	private PostRepository postRepository;
	
	@Override
	public PostDetailPayload getPostDetail(long postId) {
		PostDetailPayload postDetailPayload = new PostDetailPayload();
		Post post = postRepository.getById(postId);
		postDetailPayload.setPostId(post.getId());
		
		List<String> imagesSrc = new LinkedList<>();
		for (Image image : post.getImages()) {
			imagesSrc.add(image.getSource());
		}
		postDetailPayload.setImages(imagesSrc);
		
		List<String> videosSrc = new LinkedList<>();
		for (Video video : post.getVideos()) {
			videosSrc.add(video.getSource());
		}
		postDetailPayload.setVideos(videosSrc);
		
		postDetailPayload.setProvince(post.getAccomodation().getProvince());
		postDetailPayload.setDistrict(post.getAccomodation().getDicstrict());
		postDetailPayload.setWard(post.getAccomodation().getWard());
		postDetailPayload.setStreet(post.getAccomodation().getStreet());
		postDetailPayload.setTitle(post.getTitle());
		postDetailPayload.setPrice(post.getAccomodation().getPrice());
		postDetailPayload.setArea(post.getAccomodation().getArea());
		postDetailPayload.setNumOfBed(post.getAccomodation().getNumOfBed());
		postDetailPayload.setNumOfFloor(post.getAccomodation().getNumOfFloor());
		postDetailPayload.setNumOfToilet(post.getAccomodation().getNumOfToilet());
		postDetailPayload.setFloorNumber(post.getAccomodation().getFloorNumber());
		postDetailPayload.setFurniture(post.getAccomodation().getFurniture());
		postDetailPayload.setInternet(post.getAccomodation().isInternet());
		postDetailPayload.setParking(post.getAccomodation().isParking());
		postDetailPayload.setBalcony(post.getAccomodation().isBalcony());
		postDetailPayload.setAirConditioner(post.getAccomodation().isAirConditioner());
		postDetailPayload.setHeater(post.getAccomodation().isHeater());
		postDetailPayload.setTower(post.getAccomodation().getTower());
		postDetailPayload.setDescription(post.getDescription());
		postDetailPayload.setTypeOfPost(post.getAccomodation().getTypeOfAcc().getName());
		postDetailPayload.setStartedDate(post.getStartedDate().toString());
		postDetailPayload.setClosedDate(post.getClosedDate().toString());
		postDetailPayload.setTypeExpense(post.getExpense().getType());
		postDetailPayload.setDirectionBalcony(post.getAccomodation().getDirectionBalcony());
		postDetailPayload.setDirectionHouse(post.getAccomodation().getDirectionHouse());
		postDetailPayload.setFullname(post.getUser().getFullname());
		postDetailPayload.setOtherPost(post.getUser().getPosts().size());
		postDetailPayload.setPhone(post.getUser().getPhone());
		postDetailPayload.setAvatar(post.getUser().getAvatar() != null ? post.getUser().getAvatar().getSource() : "");
		
		return postDetailPayload;
	}
}
