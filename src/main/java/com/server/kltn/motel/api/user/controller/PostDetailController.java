package com.server.kltn.motel.api.user.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.server.kltn.motel.api.user.payload.PostDetailPayload;
import com.server.kltn.motel.api.user.payload.paymentDetail.CountRelatedNews;
import com.server.kltn.motel.api.user.payload.paymentDetail.HightExpenseRelated;
import com.server.kltn.motel.api.user.payload.paymentDetail.RelatedNews;
import com.server.kltn.motel.service.PostDetailService;

@RestController
@RequestMapping("/api")
public class PostDetailController {
	
	@Autowired
	private PostDetailService postDetailService;
	
	@GetMapping(value = "/auth/get-post-detail/{postId}")
	public ResponseEntity<PostDetailPayload> 
		getTotalNewsOfProvince(@PathVariable(value = "postId") long postId){
		PostDetailPayload postDetailPayload = postDetailService.getPostDetail(postId);
		return new ResponseEntity<PostDetailPayload>(postDetailPayload, HttpStatus.OK);
	}
	
	@GetMapping(value = "/auth/get-related-type-district")
	public ResponseEntity<List<CountRelatedNews>> 
		getNewsRelatedTypeAndDistrict(@RequestParam("type") String nameType,
				@RequestParam("district") String district){
		List<CountRelatedNews> countRelatedNews = postDetailService.getRelatedNEws(nameType, district);
		return new ResponseEntity<List<CountRelatedNews>>(countRelatedNews, HttpStatus.OK);
	}
	
	@GetMapping(value = "/auth/get-hight-expense-news")
	public ResponseEntity<?> getHightExpenseNewsOfDistrict(){
		List<HightExpenseRelated> hightExpenseRelateds = postDetailService.getHightExpenseRelateds();
		return new ResponseEntity<List<HightExpenseRelated>>(hightExpenseRelateds, HttpStatus.OK);
	}
	
	@GetMapping(value = "/auth/get-related")
	public ResponseEntity<?> getRelatedNews(@RequestParam("province") String province,
			@RequestParam("district") String district){
		List<RelatedNews> relatedNews = postDetailService.getRelatedNews(province,district);
		return new ResponseEntity<List<RelatedNews>>(relatedNews, HttpStatus.OK);
	}
}
