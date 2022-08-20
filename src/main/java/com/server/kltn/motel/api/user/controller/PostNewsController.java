package com.server.kltn.motel.api.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.kltn.motel.entity.TypeOfAcc;
import com.server.kltn.motel.service.TypeOffAccService;

@RestController
@RequestMapping(value = {"/api"})
public class PostNewsController {
	
	@Autowired
	private TypeOffAccService typeOffAccService;
	
	@GetMapping("/auth/get-type-of-accomodation")
	public ResponseEntity<List<TypeOfAcc>>getListTypeOfAccomodation(){
		return new ResponseEntity<List<TypeOfAcc>>(typeOffAccService.getListTypeOfAcc(), HttpStatus.OK);
	}
}
