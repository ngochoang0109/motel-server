package com.server.kltn.motel.service.impl;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.server.kltn.motel.api.user.payload.PostDetailPayload;
import com.server.kltn.motel.api.user.payload.paymentDetail.CountRelatedNews;
import com.server.kltn.motel.api.user.payload.paymentDetail.HightExpenseRelated;
import com.server.kltn.motel.api.user.payload.paymentDetail.RelatedNews;
import com.server.kltn.motel.common.HandleDateCommon;
import com.server.kltn.motel.entity.Accomodation;
import com.server.kltn.motel.entity.Image;
import com.server.kltn.motel.entity.Post;
import com.server.kltn.motel.entity.Video;
import com.server.kltn.motel.repository.AccomodationRepo;
import com.server.kltn.motel.repository.PostRepository;
import com.server.kltn.motel.service.PostDetailService;

@Service
public class PostDetailServiceImpl implements PostDetailService{
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private AccomodationRepo accomodationRepo;
	
	@Autowired
	private HandleDateCommon handleDateCommon;
	
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
	
	@Override
	public List<CountRelatedNews> getRelatedNEws(String type, String district) {
		List<CountRelatedNews> accomodations = 
				accomodationRepo.getReleatedNewsOfTypeAndDistrict(type, district, handleDateCommon.getCurrentDateTime());
		return accomodations;
	}
	
	@Override
	public List<HightExpenseRelated> getHightExpenseRelateds() {
		 List<HightExpenseRelated> hightExpenseRelateds = 
				accomodationRepo.getPostHighExpense(handleDateCommon.getCurrentDateTime(), Arrays.asList((long)5,(long)4)).stream().limit(20).toList();
		return hightExpenseRelateds;
	}
	
	@Override
	public List<RelatedNews> getRelatedNews(String province, String district, long idPost) {
		List<Accomodation> accomodations = 
				accomodationRepo.getRelatedNews(province, 
												district, 
												handleDateCommon.getCurrentDateTime(),
												idPost).stream().limit(6).toList();
		List<RelatedNews> relatedNews = new LinkedList<RelatedNews>();
		for (Accomodation acc : accomodations) {
			RelatedNews r = new RelatedNews();
			r.setId(acc.getPost().getId());
			r.setArea(acc.getArea());
			r.setDistrict(acc.getDicstrict());
			r.setPrice(acc.getPrice());
			r.setProvince(acc.getProvince());
			r.setTitle(acc.getPost().getTitle());
			r.setStartedDate(acc.getPost().getStartedDate().toString());
			r.setImage(acc.getPost().getImages().get(0).getSource());
			relatedNews.add(r);
		}
		return relatedNews;
	}
}
