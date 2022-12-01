package com.server.kltn.motel.api.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.kltn.motel.api.user.payload.NewsCard;
import com.server.kltn.motel.api.user.payload.HomePayload.CountNews;
import com.server.kltn.motel.api.user.payload.HomePayload.SearchParam;
import com.server.kltn.motel.constant.PageAndSortConstant;
import com.server.kltn.motel.page.Page;
import com.server.kltn.motel.service.HomeService;

@RestController
@RequestMapping("/api")
public class HomeController {
	@Autowired
	private HomeService homeService;
	
	@GetMapping(value = "/auth/menu-news")
	public ResponseEntity<Page<NewsCard>> searchNews(
			@RequestParam(value = "pageNo", defaultValue = PageAndSortConstant.PAGE_NO, required = false) int pageNo, 
			@RequestParam(value = "pageSize", defaultValue = PageAndSortConstant.PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sort", defaultValue = PageAndSortConstant.SORT, required = false) String field,
			@RequestParam(value = "mode", defaultValue = PageAndSortConstant.MODE, required = false) int mode,
			@RequestParam(value = "type", defaultValue = "0", required = false) long type,
			@RequestParam(value = "province", defaultValue = "", required = false) String province,
			@RequestParam(value = "district", defaultValue = "", required = false) String district,
			@RequestParam(value = "ward", defaultValue = "", required = false) String ward,
			@RequestParam(value = "priceFrom", defaultValue = "0", required = false) long priceFrom,
			@RequestParam(value = "priceTo", defaultValue = "0", required = false) long priceTo,
			@RequestParam(value = "areaFrom", defaultValue = "0", required = false) int areaFrom,
			@RequestParam(value = "areaTo", defaultValue = "0", required = false) int areaTo){
		SearchParam searchParam= new SearchParam();
		searchParam.setType(type);
		searchParam.setProvince(province);
		searchParam.setDistrict(district);
		searchParam.setWard(ward);
		searchParam.setPriceFrom(priceFrom);
		searchParam.setPriceTo(priceTo);
		searchParam.setAreaFrom(areaFrom);
		searchParam.setAreaTo(areaTo);
		Page<NewsCard> posts= homeService.searchPageCard(pageNo, pageSize, field, mode, searchParam);
		return new ResponseEntity<Page<NewsCard>>(posts, HttpStatus.OK);
	}
	
	@GetMapping(value = "/auth/menu-news/total-news-province")
	public ResponseEntity<List<CountNews>> getTotalNewsOfProvince(){
		return new ResponseEntity<List<CountNews>>(homeService.getCountByProvince(), HttpStatus.OK);
	}
}
