package com.server.kltn.motel.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.server.kltn.motel.api.user.payload.AccomodationInfor;
import com.server.kltn.motel.api.user.payload.CostCalculate;
import com.server.kltn.motel.api.user.payload.NewsInfor;
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
			post.setStartedDate(handleDateCommon.convertStringDateToLocalDateTime(costCalculate.getStartedDate()).plusHours(DateTimeConstant.UtcTimeZoneVN));
			LocalDateTime closedDate = handleDateCommon.convertStringDateToLocalDateTime(costCalculate.getStartedDate()).plusHours(DateTimeConstant.UtcTimeZoneVN)
					.plusDays(costCalculate.getNumDatePost());
			post.setClosedDate(closedDate);
			
			List<Expense> expenses= expenseRepository.findByIdIn(
					new ArrayList<Long>(Arrays.asList(newsInfor.getTypeOfPost())));
			post.setExpense(expenses.get(0));
			
			post.setDiscount(discountRepository.getByCode(newsInfor.getDiscount()));
			
			User user= userRepository.findByUsernameOrEmail(username,username)
							.orElseThrow(() -> new ResourceNotFoundException("user", "username", username));
			post.setUser(user);
			post.setStatus(0);
			post.setApprovedDate(null);
			post.setIsPayment(false);
			
			// set Accomodation Object
			Accomodation accomodation = postNewsMapper.mapAccomodationInforToAccomodation(accomodationInfor);
			List<TypeOfAcc> typeOfAccs = typeOfAccRepository
					.findByIdIn(new ArrayList<Long>(Arrays.asList(accomodationInfor.getTypesOfAcc())));
			accomodation.setTypeOfAcc(typeOfAccs.get(0));
			
			post.setAccomodation(accomodation);
			accomodation.setPost(post);
			post=postRepository.save(post);
			
			if (images != null) {
				// set value for image
				List<String> urlImages = awsS3Common.uploadMulFile(images);
				for (Image image : postNewsMapper.convertUrlImagesToImages(urlImages)) {
					image.setPost(post);
					imageRepository.save(image);
				}
			}
			
			if (newsInfor.getVideos()!=null) {
				List<Video> videos = new ArrayList<>();
				for (String strV : newsInfor.getVideos()) {
					videos.add(new Video(strV, post));
				}
				List<Video> newVideos= videoRepository.saveAll(videos);
				post.setVideos(newVideos);
			}
		}
		catch (Exception e) {
			System.out.print(e.getMessage());
			throw new ServerException("Lỗi tạo tin, vui lòng điền đầy đủ thông tin");
		}
	}
}
