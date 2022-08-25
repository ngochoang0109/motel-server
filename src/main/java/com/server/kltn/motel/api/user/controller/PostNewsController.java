package com.server.kltn.motel.api.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.kltn.motel.api.user.payload.UserInfor;
import com.server.kltn.motel.entity.TypeOfAcc;
import com.server.kltn.motel.entity.User;
import com.server.kltn.motel.service.TypeOffAccService;
import com.server.kltn.motel.service.UserService;

@RestController
@RequestMapping(value = {"/api"})
public class PostNewsController {
	
	@Autowired
	private TypeOffAccService typeOffAccService;
	
	@Autowired
	private UserService userService;
	
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
}
