package com.server.kltn.motel.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.server.kltn.motel.api.user.payload.NewsFormPayload.AccomodationInfor;
import com.server.kltn.motel.api.user.payload.NewsFormPayload.CostCalculate;
import com.server.kltn.motel.api.user.payload.NewsFormPayload.DetailNews;
import com.server.kltn.motel.api.user.payload.NewsFormPayload.NewsInfor;
import com.server.kltn.motel.common.AwsS3Common;
import com.server.kltn.motel.common.HandleDateCommon;
import com.server.kltn.motel.constant.DateTimeConstant;
import com.server.kltn.motel.entity.Accomodation;
import com.server.kltn.motel.entity.Expense;
import com.server.kltn.motel.entity.Image;
import com.server.kltn.motel.entity.Post;
import com.server.kltn.motel.entity.TypeOfAcc;
import com.server.kltn.motel.entity.User;
import com.server.kltn.motel.entity.Video;
import com.server.kltn.motel.exception.BadRequestException;
import com.server.kltn.motel.exception.ResourceNotFoundException;
import com.server.kltn.motel.exception.ServerException;
import com.server.kltn.motel.mapper.PostNewsMapper;
import com.server.kltn.motel.repository.DiscountRepository;
import com.server.kltn.motel.repository.ExpenseRepository;
import com.server.kltn.motel.repository.ImageRepository;
import com.server.kltn.motel.repository.PostRepository;
import com.server.kltn.motel.repository.TypeOfAccRepository;
import com.server.kltn.motel.repository.UserRepository;
import com.server.kltn.motel.repository.VideoRepository;
import com.server.kltn.motel.service.PostNewsService;

@Service
public class PostNewsImpl implements PostNewsService {

	@Autowired
	private PostNewsMapper postNewsMapper;

	@Autowired
	private TypeOfAccRepository typeOfAccRepository;

	@Autowired
	private HandleDateCommon handleDateCommon;

	@Autowired
	private AwsS3Common awsS3Common;

	@Autowired
	private ExpenseRepository expenseRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private VideoRepository videoRepository;

	@Autowired
	private DiscountRepository discountRepository;

	@Override
	@Transactional
	public void createNews(NewsInfor newsInfor, AccomodationInfor accomodationInfor, CostCalculate costCalculate,
			List<MultipartFile> images, String username) {
		try {
			// set Post Object
			Post post = postNewsMapper.mapNewsInforToPost(newsInfor);
			post.setCreatedDate(handleDateCommon.getCurrentDateTime());
			post.setStartedDate(handleDateCommon.convertStringDateToLocalDateTime(costCalculate.getStartedDate())
					.plusHours(DateTimeConstant.UtcTimeZoneVN));
			LocalDateTime closedDate = handleDateCommon.convertStringDateToLocalDateTime(costCalculate.getStartedDate())
					.plusHours(DateTimeConstant.UtcTimeZoneVN).plusDays(costCalculate.getNumDatePost());
			post.setClosedDate(closedDate);

			List<Expense> expenses = expenseRepository
					.findByIdIn(new ArrayList<Long>(Arrays.asList(newsInfor.getTypeOfPost())));
			post.setExpense(expenses.get(0));

			post.setDiscount(discountRepository.getByCode(newsInfor.getDiscount()));

			User user = userRepository.findByUsernameOrEmail(username, username)
					.orElseThrow(() -> new ResourceNotFoundException("user", "username", username));
			post.setUser(user);
			post.setStatus(0);
			post.setApprovedDate(null);
			post.setIsPayment(false);

			// set Accommodation Object
			Accomodation accomodation = postNewsMapper.mapAccomodationInforToAccomodation(accomodationInfor);
			List<TypeOfAcc> typeOfAccs = typeOfAccRepository
					.findByIdIn(new ArrayList<Long>(Arrays.asList(accomodationInfor.getTypesOfAcc())));
			accomodation.setTypeOfAcc(typeOfAccs.get(0));

			post.setAccomodation(accomodation);
			accomodation.setPost(post);
			post = postRepository.save(post);

			if (images != null) {
				// set value for image
				List<String> urlImages = awsS3Common.uploadMulFile(images);
				for (Image image : postNewsMapper.convertUrlImagesToImages(urlImages)) {
					image.setPost(post);
					imageRepository.save(image);
				}
			}

			if (newsInfor.getVideos() != null) {
				List<Video> videos = new ArrayList<>();
				for (String strV : newsInfor.getVideos()) {
					videos.add(new Video(strV, post));
				}
				List<Video> newVideos = videoRepository.saveAll(videos);
				post.setVideos(newVideos);
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
			throw new ServerException("Lỗi tạo tin, vui lòng điền đầy đủ thông tin");
		}
	}

	@Override
	public DetailNews getDetailNews(long postId) {

		DetailNews detailNews = new DetailNews();
		AccomodationInfor accomodationInfor = new AccomodationInfor();
		NewsInfor newsInfor = new NewsInfor();

		Post post = postRepository.getById(postId);

		accomodationInfor.setAirConditioner(post.getAccomodation().isAirConditioner());
		accomodationInfor.setArea(post.getAccomodation().getArea());
		accomodationInfor.setBalcony(post.getAccomodation().isBalcony());
		accomodationInfor.setDirectionBalcony(post.getAccomodation().getDirectionBalcony());
		accomodationInfor.setDirectionHouse(post.getAccomodation().getDirectionHouse());
		accomodationInfor.setDistrict(post.getAccomodation().getDicstrict());
		accomodationInfor.setFloorNumber(post.getAccomodation().getFloorNumber());
		accomodationInfor.setFurniture(post.getAccomodation().getFurniture());
		accomodationInfor.setHeater(post.getAccomodation().isHeater());
		accomodationInfor.setInternet(post.getAccomodation().isInternet());
		accomodationInfor.setNumOfBed(post.getAccomodation().getNumOfBed());
		accomodationInfor.setNumOfFloor(post.getAccomodation().getNumOfFloor());
		accomodationInfor.setNumOfToilet(post.getAccomodation().getNumOfToilet());
		accomodationInfor.setParking(post.getAccomodation().isParking());
		accomodationInfor.setPrice(post.getAccomodation().getPrice());
		accomodationInfor.setProvince(post.getAccomodation().getProvince());
		accomodationInfor.setStreet(post.getAccomodation().getStreet());
		accomodationInfor.setTower(post.getAccomodation().getTower());
		accomodationInfor.setTypesOfAcc(post.getAccomodation().getTypeOfAcc().getId());
		accomodationInfor.setWard(post.getAccomodation().getWard());

		newsInfor.setTitle(post.getTitle());
		newsInfor.setDescription(post.getDescription());

		List<String> videos = new LinkedList<>();
		for (Video video : post.getVideos()) {
			videos.add("https://www.youtube.com/watch?v=" + video.getSource());
		}
		newsInfor.setVideos(videos);

		List<String> images = new LinkedList<>();
		for (Image image : post.getImages()) {
			images.add(image.getSource());
		}

		detailNews.setAccomodationInfor(accomodationInfor);
		detailNews.setNewsInfor(newsInfor);
		detailNews.setImages(images);

		return detailNews;
	}

	@Override
	@Transactional
	public void extendedTimeToPost(long postId, CostCalculate costCalculate, String username) {
		try {
			// set Post Object
			Post post = postRepository.getById(postId);

			Post extendedPost = new Post();
			extendedPost.setCreatedDate(handleDateCommon.getCurrentDateTime());
			extendedPost
					.setStartedDate(handleDateCommon.convertStringDateToLocalDateTime(costCalculate.getStartedDate())
							.plusHours(DateTimeConstant.UtcTimeZoneVN));
			LocalDateTime closedDate = handleDateCommon.convertStringDateToLocalDateTime(costCalculate.getStartedDate())
					.plusHours(DateTimeConstant.UtcTimeZoneVN).plusDays(costCalculate.getNumDatePost());
			extendedPost.setClosedDate(closedDate);

			List<Expense> expenses = expenseRepository
					.findByIdIn(new ArrayList<Long>(Arrays.asList(costCalculate.getTypeOfPost())));
			extendedPost.setExpense(expenses.get(0));

			extendedPost.setDiscount(discountRepository.getByCode(costCalculate.getDiscount()));

			User user = userRepository.findByUsernameOrEmail(username, username)
					.orElseThrow(() -> new ResourceNotFoundException("user", "username", username));
			extendedPost.setUser(user);
			extendedPost.setStatus(1);
			extendedPost.setApprovedDate(handleDateCommon.getCurrentDateTime());
			extendedPost.setIsPayment(false);
			extendedPost.setTitle(post.getTitle());
			extendedPost.setDescription(post.getDescription());

			int numDate = (int) ChronoUnit.DAYS.between(extendedPost.getStartedDate(), extendedPost.getClosedDate());
			long amountPost = extendedPost.getExpense().getCost() * numDate;
			long amountDiscounted = 0;
			if (extendedPost.getDiscount() != null) {
				amountDiscounted = (amountPost * extendedPost.getDiscount().getPercent()) / 100;
				if (amountDiscounted > extendedPost.getDiscount().getPrice()) {
					amountDiscounted = extendedPost.getDiscount().getPrice();
					amountPost = amountPost - extendedPost.getDiscount().getPrice();
				} else {
					amountPost = amountPost - amountDiscounted;
				}
			}
			extendedPost.setTotalAmount(amountPost);

			// set Accommodation Object
			Accomodation accomodation = new Accomodation();
			accomodation.setAirConditioner(post.getAccomodation().isAirConditioner());
			accomodation.setArea(post.getAccomodation().getArea());
			accomodation.setBalcony(post.getAccomodation().isBalcony());
			accomodation.setDicstrict(post.getAccomodation().getDicstrict());
			accomodation.setDirectionBalcony(post.getAccomodation().getDirectionBalcony());
			accomodation.setDirectionHouse(post.getAccomodation().getDirectionHouse());
			accomodation.setFloorNumber(post.getAccomodation().getFloorNumber());
			accomodation.setFurniture(post.getAccomodation().getFurniture());
			accomodation.setHeater(post.getAccomodation().isHeater());
			accomodation.setInternet(post.getAccomodation().isInternet());
			accomodation.setNumOfBed(post.getAccomodation().getNumOfBed());
			accomodation.setNumOfFloor(post.getAccomodation().getNumOfFloor());
			accomodation.setNumOfToilet(post.getAccomodation().getNumOfToilet());
			accomodation.setParking(post.getAccomodation().isParking());
			accomodation.setPrice(post.getAccomodation().getPrice());
			accomodation.setProvince(post.getAccomodation().getProvince());
			accomodation.setStreet(post.getAccomodation().getStreet());
			accomodation.setTower(post.getAccomodation().getTower());
			accomodation.setTypeOfAcc(post.getAccomodation().getTypeOfAcc());
			accomodation.setWard(post.getAccomodation().getWard());

			extendedPost.setAccomodation(accomodation);
			accomodation.setPost(extendedPost);
			extendedPost.setImages(post.getImages());
			extendedPost.setVideos(post.getVideos());

			post.setStatus(4);
			post = postRepository.save(post);

			List<Image> images = new LinkedList<>();
			if (post.getImages() != null) {
				for (Image image : post.getImages()) {
					Image newImage = new Image();
					newImage.setSource(image.getSource());
					newImage.setType(image.getType());
					newImage.setPost(extendedPost);
					images.add(newImage);
				}
			}
			extendedPost.setImages(images);

			List<Video> videos = new LinkedList<>();
			if (post.getVideos() != null) {
				for (Video video : post.getVideos()) {
					Video newVideo = new Video();
					newVideo.setSource(video.getSource());
					newVideo.setPost(extendedPost);
					videos.add(newVideo);
				}
			}
			extendedPost.setVideos(videos);
			extendedPost = postRepository.save(extendedPost);
		} catch (Exception e) {
			System.out.print(e.getMessage());
			throw new ServerException("Gia hạn bài viết thất bại!!");
		}
	}

	@Override
	@Transactional
	public void editNews(NewsInfor newsInfor, AccomodationInfor accomodationInfor, CostCalculate costCalculate,
			List<MultipartFile> images, String username, long postId) {
		try {
			imageRepository.deletedImagesOfPost(postId);
			videoRepository.deletedVideosOfPost(postId);
			Post post = postRepository.getById(postId);
			// set Post Object
			post.setTitle(newsInfor.getTitle());
			post.setDescription(newsInfor.getDescription());
			post.setTotalAmount(newsInfor.getTotalAmount());
			post.setCreatedDate(handleDateCommon.getCurrentDateTime());
			post.setStartedDate(handleDateCommon.convertStringDateToLocalDateTime(costCalculate.getStartedDate())
					.plusHours(DateTimeConstant.UtcTimeZoneVN));
			LocalDateTime closedDate = handleDateCommon.convertStringDateToLocalDateTime(costCalculate.getStartedDate())
					.plusHours(DateTimeConstant.UtcTimeZoneVN).plusDays(costCalculate.getNumDatePost());
			post.setClosedDate(closedDate);

			List<Expense> expenses = expenseRepository
					.findByIdIn(new ArrayList<Long>(Arrays.asList(newsInfor.getTypeOfPost())));
			post.setExpense(expenses.get(0));
			post.setDiscount(discountRepository.getByCode(newsInfor.getDiscount()));
			User user = userRepository.findByUsernameOrEmail(username, username)
					.orElseThrow(() -> new ResourceNotFoundException("user", "username", username));
			post.setUser(user);
			post.setStatus(0);
			post.setApprovedDate(null);
			post.setIsPayment(false);

			// set Accommodation Object
			Accomodation accomodation = post.getAccomodation();
			accomodation.setAirConditioner(accomodationInfor.isAirConditioner());
			accomodation.setArea(accomodationInfor.getArea());
			accomodation.setBalcony(accomodationInfor.isBalcony());
			accomodation.setDicstrict(accomodationInfor.getDistrict());
			accomodation.setDirectionBalcony(accomodationInfor.getDirectionBalcony());
			accomodation.setDirectionHouse(accomodationInfor.getDirectionHouse());
			accomodation.setFloorNumber(accomodationInfor.getFloorNumber());
			accomodation.setFurniture(accomodationInfor.getFurniture());
			accomodation.setHeater(accomodationInfor.isHeater());
			accomodation.setInternet(accomodationInfor.isInternet());
			accomodation.setNumOfBed(accomodationInfor.getNumOfBed());
			accomodation.setNumOfFloor(accomodationInfor.getNumOfFloor());
			accomodation.setNumOfToilet(accomodationInfor.getNumOfToilet());
			accomodation.setParking(accomodationInfor.isParking());
			accomodation.setPrice(accomodationInfor.getPrice());
			accomodation.setProvince(accomodationInfor.getProvince());
			accomodation.setStreet(accomodationInfor.getStreet());
			accomodation.setTower(accomodationInfor.getTower());
			accomodation.setWard(accomodationInfor.getWard());
			List<TypeOfAcc> typeOfAccs = typeOfAccRepository
					.findByIdIn(new ArrayList<Long>(Arrays.asList(accomodationInfor.getTypesOfAcc())));
			accomodation.setTypeOfAcc(typeOfAccs.get(0));
			post = postRepository.save(post);

			if (images != null) {
				// set value for image
				List<String> urlImages = awsS3Common.uploadMulFile(images);
				for (Image image : postNewsMapper.convertUrlImagesToImages(urlImages)) {
					image.setPost(post);
					imageRepository.save(image);
				}
			}

			if (newsInfor.getVideos() != null) {
				List<Video> videos = new ArrayList<>();
				for (String strV : newsInfor.getVideos()) {
					videos.add(new Video(strV, post));
				}
				List<Video> newVideos = videoRepository.saveAll(videos);
				post.setVideos(newVideos);
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
			throw new BadRequestException("Lỗi chỉnh sữa, vui lòng điền đầy đủ thông tin");
		}
	}
}
