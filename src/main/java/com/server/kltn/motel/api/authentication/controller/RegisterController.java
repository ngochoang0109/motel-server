package com.server.kltn.motel.api.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.kltn.motel.api.authentication.payload.NewUserDataset;
import com.server.kltn.motel.common.MessageCommon;
import com.server.kltn.motel.service.AuthenService;

@RestController
@RequestMapping(value = {"/api/auth"})
public class RegisterController {
	
	@Autowired
	private AuthenService authenService;
	
	@PostMapping("/register")
	public ResponseEntity<MessageCommon> registerUser(@RequestBody NewUserDataset newUserDataset){
		MessageCommon messageCommon= authenService.registerUser(newUserDataset);
		return new ResponseEntity<>(messageCommon,HttpStatus.OK);
	}
}