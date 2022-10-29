package com.server.kltn.motel.api.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.kltn.motel.api.authentication.payload.UserInfor;
import com.server.kltn.motel.service.UserService;

@RestController()
@RequestMapping("/api")
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/user/infor")
	@PreAuthorize("hasRole('ROLE_USER')"+"||" + "hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> getCurrentUserInfor(Authentication authentication){
		return new ResponseEntity<UserInfor>(userService.getCurrentUser(authentication.getName()), HttpStatus.OK);
	}
}
