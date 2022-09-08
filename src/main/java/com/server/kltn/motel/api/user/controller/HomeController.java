package com.server.kltn.motel.api.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.kltn.motel.api.user.payload.NewsCard;
import com.server.kltn.motel.constant.PageAndSortConstant;
import com.server.kltn.motel.page.Page;
import com.server.kltn.motel.service.HomeService;

@RestController
@RequestMapping("/api")
public class HomeController {
	@Autowired
	private HomeService homeService;
	
	@GetMapping(value = "/auth/menu-news")
	public ResponseEntity<Page<NewsCard>> getPosts(
			@RequestParam(value = "pageNo", defaultValue = PageAndSortConstant.PAGE_NO, required = false) int pageNo, 
			@RequestParam(value = "pageSize", defaultValue = PageAndSortConstant.PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sort", defaultValue = PageAndSortConstant.SORT, required = false) String field,
			@RequestParam(value = "mode", defaultValue = PageAndSortConstant.MODE, required = false) int mode){
		Page<NewsCard> posts= homeService.getPageNewsCard(pageNo, pageSize, field, mode);
		return new ResponseEntity<Page<NewsCard>>(posts, HttpStatus.OK);
	}
}
