package com.server.kltn.motel.api.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.server.kltn.motel.api.admin.payload.DiscountDatasource;
import com.server.kltn.motel.api.admin.payload.ExpenseDatasource;
import com.server.kltn.motel.api.user.payload.AccomodationInfor;
import com.server.kltn.motel.api.user.payload.CostCalculate;
import com.server.kltn.motel.api.user.payload.NewsInfor;
import com.server.kltn.motel.api.user.payload.UserInfor;
import com.server.kltn.motel.entity.Post;
import com.server.kltn.motel.entity.TypeOfAcc;
import com.server.kltn.motel.entity.User;
import com.server.kltn.motel.service.DiscountManagementService;
import com.server.kltn.motel.service.ExpenseService;
import com.server.kltn.motel.service.PostNewsService;
import com.server.kltn.motel.service.TypeOffAccService;
import com.server.kltn.motel.service.UserService;

@RestController
@RequestMapping(value = {"/api"})
public class PostNewsController {
	
	@Autowired
	private TypeOffAccService typeOffAccService;
	
	@Autowired
	private ExpenseService expenseService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PostNewsService postNewsService;
	
	@Autowired
	private DiscountManagementService discountManagementService;
	
	@GetMapping("/auth/get-type-of-accomodation")
	public ResponseEntity<List<TypeOfAcc>>getListTypeOfAccomodation(){
		return new ResponseEntity<List<TypeOfAcc>>(typeOffAccService.getListTypeOfAcc(), HttpStatus.OK);
	}
	
	@GetMapping("/current-user")
	@PreAuthorize("hasRole('ROLE_USER')"+"||"+"hasRole('ROLE_ADMIN')")
	public ResponseEntity<UserInfor> getCurrentUser(Authentication authentication){
		User user=userService.findUserByUsernameOrEmail(authentication.getName(),authentication.getName());
		return new ResponseEntity<UserInfor>(new UserInfor(user.getFullname(),user.getPhone(),
									user.getAddress(), user.getEmail()),HttpStatus.OK);
	}
	
	@GetMapping("/auth/get-type-of-post")
	public ResponseEntity<List<ExpenseDatasource>> getExpenses(){
		return new ResponseEntity<List<ExpenseDatasource>>(expenseService.getExpenses(),HttpStatus.OK);
	}
	
	@GetMapping("/auth/get-discount-of-expense")
	public ResponseEntity<List<DiscountDatasource>> getDiscountOfExpense(@RequestParam Long id){
		return new ResponseEntity<List<DiscountDatasource>>(discountManagementService.getDiscountOfExpense(id), HttpStatus.OK);
	}
	
	@PostMapping("/post-news/create")
	@PreAuthorize("hasRole('ROLE_USER')"+"||"+"hasRole('ROLE_ADMIN')")
	public ResponseEntity<Post> createNewsPost(Authentication authentication,
													@RequestPart NewsInfor postNews,
													@RequestPart AccomodationInfor accomodationInfor,
													@RequestPart CostCalculate costCalculate,
													@RequestPart(value = "images", required = false) List<MultipartFile> images){
		postNewsService.createNews(postNews, accomodationInfor, costCalculate,images, authentication.getName());
		return new ResponseEntity<Post>(new Post(),HttpStatus.OK);
	}
}
