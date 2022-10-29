package com.server.kltn.motel.api.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.kltn.motel.api.admin.payload.RejectDatasource;
import com.server.kltn.motel.api.user.payload.NewsCard;
import com.server.kltn.motel.service.NewsManagementService;

@RestController
@RequestMapping(value = { "/api/admin" })
public class NewsManagement {

	@Autowired
	private NewsManagementService newsManagementService;

	@GetMapping("/news-management-list-data")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<NewsCard>> getAllDiscount(@RequestParam(value = "mode") String mode) {
		List<NewsCard> posts = newsManagementService.getNewsByStatus(mode);
		return new ResponseEntity<List<NewsCard>>(posts, HttpStatus.OK);
	}
	
	@PostMapping("/news-management-list-data/approved-news")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Boolean> approvedNews(@RequestParam("id") long id) {
		Boolean result = newsManagementService.approvedNews(id);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
	
	@PostMapping("/news-management-list-data/reject-news")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Boolean> rejectedNews(@RequestParam("id") long id) {
		Boolean result = newsManagementService.rejectedNews(id);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
	
	@PostMapping("/news-management-list-data/send-reason")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Boolean> insertReasonNews(@RequestBody RejectDatasource rejectDatasource) {
		Boolean result = newsManagementService.insertReason(rejectDatasource);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
	
	@GetMapping("/news-management-list-data/show-reason")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> selReason(@RequestParam("id") long id) {
		String result = newsManagementService.selReason(id);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
}