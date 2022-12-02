package com.server.kltn.motel.api.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.kltn.motel.api.authentication.payload.UserInfor;
import com.server.kltn.motel.service.UserService;

@RestController
@RequestMapping(value = {"/api"})
public class UserManagement {
	
	@Autowired
	private UserService userService;

	@GetMapping("/account-management")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<UserInfor>> getAllUser(){
		return new ResponseEntity<List<UserInfor>>(userService.getAllUser(), HttpStatus.OK);
	}
}
