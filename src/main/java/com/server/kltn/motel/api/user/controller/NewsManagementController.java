package com.server.kltn.motel.api.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.server.kltn.motel.api.user.payload.FilterParam;
import com.server.kltn.motel.api.user.payload.NewsCard;
import com.server.kltn.motel.constant.PageAndSortConstant;
import com.server.kltn.motel.page.Page;
import com.server.kltn.motel.service.NewsManagementService;

@RestController
@RequestMapping("/api")
public class NewsManagementController {

	@Autowired
	private NewsManagementService newsManagementService;

	@GetMapping(value = "/news-management/get-all-of-user")
	@PreAuthorize("hasRole('ROLE_USER')" + "||" + "hasRole('ROLE_ADMIN')")
	public ResponseEntity<Page<NewsCard>> getPosts(
			@RequestParam(value = "pageNo", defaultValue = PageAndSortConstant.PAGE_NO, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = PageAndSortConstant.PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sort", defaultValue = PageAndSortConstant.SORT, required = false) String field,
			@RequestParam(value = "mode", defaultValue = PageAndSortConstant.MODE, required = false) int mode,
			Authentication authentication) {
		Page<NewsCard> posts = newsManagementService.getAllNewsOfUser(pageNo, pageSize, field, mode,
				authentication.getName());
		return new ResponseEntity<Page<NewsCard>>(posts, HttpStatus.OK);
	}

	@GetMapping(value = "/news-management/get-waitting-approved")
	@PreAuthorize("hasRole('ROLE_USER')" + "||" + "hasRole('ROLE_ADMIN')")
	public ResponseEntity<Page<NewsCard>> getWattingApproved(
			@RequestParam(value = "pageNo", defaultValue = PageAndSortConstant.PAGE_NO, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = PageAndSortConstant.PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sort", defaultValue = PageAndSortConstant.SORT, required = false) String field,
			@RequestParam(value = "mode", defaultValue = PageAndSortConstant.MODE, required = false) int mode,
			Authentication authentication) {
		Page<NewsCard> posts = newsManagementService.getNewsWaitingAproved(pageNo, pageSize, field, mode,
				authentication.getName());
		return new ResponseEntity<Page<NewsCard>>(posts, HttpStatus.OK);
	}

	@GetMapping(value = "/news-management/get-news-reject")
	@PreAuthorize("hasRole('ROLE_USER')" + "||" + "hasRole('ROLE_ADMIN')")
	public ResponseEntity<Page<NewsCard>> getRejectOfUser(
			@RequestParam(value = "pageNo", defaultValue = PageAndSortConstant.PAGE_NO, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = PageAndSortConstant.PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sort", defaultValue = PageAndSortConstant.SORT, required = false) String field,
			@RequestParam(value = "mode", defaultValue = PageAndSortConstant.MODE, required = false) int mode,
			Authentication authentication) {
		Page<NewsCard> posts = newsManagementService.getNewsRejectOfUser(pageNo, pageSize, field, mode,
				authentication.getName());
		return new ResponseEntity<Page<NewsCard>>(posts, HttpStatus.OK);
	}

	@GetMapping(value = "/news-management/get-dont-payment")
	@PreAuthorize("hasRole('ROLE_USER')" + "||" + "hasRole('ROLE_ADMIN')")
	public ResponseEntity<Page<NewsCard>> getDontPaymentOfUser(
			@RequestParam(value = "pageNo", defaultValue = PageAndSortConstant.PAGE_NO, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = PageAndSortConstant.PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sort", defaultValue = PageAndSortConstant.SORT, required = false) String field,
			@RequestParam(value = "mode", defaultValue = PageAndSortConstant.MODE, required = false) int mode,
			Authentication authentication) {
		Page<NewsCard> posts = newsManagementService.getDontPaymentOfUser(pageNo, pageSize, field, mode,
				authentication.getName());
		return new ResponseEntity<Page<NewsCard>>(posts, HttpStatus.OK);
	}

	@GetMapping(value = "/news-management/get-waitting-show")
	@PreAuthorize("hasRole('ROLE_USER')" + "||" + "hasRole('ROLE_ADMIN')")
	public ResponseEntity<Page<NewsCard>> getWaittingShowOfUser(
			@RequestParam(value = "pageNo", defaultValue = PageAndSortConstant.PAGE_NO, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = PageAndSortConstant.PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sort", defaultValue = PageAndSortConstant.SORT, required = false) String field,
			@RequestParam(value = "mode", defaultValue = PageAndSortConstant.MODE, required = false) int mode,
			Authentication authentication) {
		Page<NewsCard> posts = newsManagementService.getWaittingShowOfUser(pageNo, pageSize, field, mode,
				authentication.getName());
		return new ResponseEntity<Page<NewsCard>>(posts, HttpStatus.OK);
	}

	@GetMapping(value = "/news-management/get-news-showing")
	@PreAuthorize("hasRole('ROLE_USER')" + "||" + "hasRole('ROLE_ADMIN')")
	public ResponseEntity<Page<NewsCard>> getNewsShowingOfUser(
			@RequestParam(value = "pageNo", defaultValue = PageAndSortConstant.PAGE_NO, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = PageAndSortConstant.PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sort", defaultValue = PageAndSortConstant.SORT, required = false) String field,
			@RequestParam(value = "mode", defaultValue = PageAndSortConstant.MODE, required = false) int mode,
			Authentication authentication) {
		Page<NewsCard> posts = newsManagementService.getNewsShowingOfUser(pageNo, pageSize, field, mode,
				authentication.getName());
		return new ResponseEntity<Page<NewsCard>>(posts, HttpStatus.OK);
	}

	@GetMapping(value = "/news-management/get-news-hidden")
	@PreAuthorize("hasRole('ROLE_USER')" + "||" + "hasRole('ROLE_ADMIN')")
	public ResponseEntity<Page<NewsCard>> getNewsHiddenOfUser(
			@RequestParam(value = "pageNo", defaultValue = PageAndSortConstant.PAGE_NO, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = PageAndSortConstant.PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sort", defaultValue = PageAndSortConstant.SORT, required = false) String field,
			@RequestParam(value = "mode", defaultValue = PageAndSortConstant.MODE, required = false) int mode,
			Authentication authentication) {
		Page<NewsCard> posts = newsManagementService.getNewsHiddenOfUser(pageNo, pageSize, field, mode,
				authentication.getName());
		return new ResponseEntity<Page<NewsCard>>(posts, HttpStatus.OK);
	}

	@GetMapping(value = "/news-management/get-news-expried")
	@PreAuthorize("hasRole('ROLE_USER')" + "||" + "hasRole('ROLE_ADMIN')")
	public ResponseEntity<Page<NewsCard>> getNewsExpriedOfUser(
			@RequestParam(value = "pageNo", defaultValue = PageAndSortConstant.PAGE_NO, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = PageAndSortConstant.PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sort", defaultValue = PageAndSortConstant.SORT, required = false) String field,
			@RequestParam(value = "mode", defaultValue = PageAndSortConstant.MODE, required = false) int mode,
			Authentication authentication) {
		Page<NewsCard> posts = newsManagementService.getNewsExpriedOfUser(pageNo, pageSize, field, mode,
				authentication.getName());
		return new ResponseEntity<Page<NewsCard>>(posts, HttpStatus.OK);
	}

	@GetMapping(value = "/news-management/get-news-text-search")
	@PreAuthorize("hasRole('ROLE_USER')" + "||" + "hasRole('ROLE_ADMIN')")
	public ResponseEntity<Page<NewsCard>> getNewsByTextSearch(
			@RequestParam(value = "pageNo", defaultValue = PageAndSortConstant.PAGE_NO, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = PageAndSortConstant.PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sort", defaultValue = PageAndSortConstant.SORT, required = false) String field,
			@RequestParam(value = "mode", defaultValue = PageAndSortConstant.MODE, required = false) int mode,
			@RequestParam(value = "status") String status, @RequestParam(value = "textSearch") String textSearch,
			@RequestBody(required = false) FilterParam filterParam, Authentication authentication) {
		Page<NewsCard> posts = newsManagementService.getNewsByTextSearch(pageNo, pageSize, field, mode,
				authentication.getName(), status, textSearch, filterParam);
		return new ResponseEntity<Page<NewsCard>>(posts, HttpStatus.OK);
	}
	
	
	@PostMapping("/news-management/update-hidden-post")
	@PreAuthorize("hasRole('ROLE_USER')" + "||" + "hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> updateHiddenStatusPost(@RequestParam("id") long id) {
		newsManagementService.updateHiddenToPost(id);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@PostMapping("/news-management/deleted-post/{postId}")
	@PreAuthorize("hasRole('ROLE_USER')" + "||" + "hasRole('ROLE_ADMIN')")
	public void deletedPost(@PathVariable(value = "postId") long postId) {
		newsManagementService.deletedPost(postId);
	}
}
