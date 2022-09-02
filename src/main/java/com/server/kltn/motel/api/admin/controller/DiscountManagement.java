package com.server.kltn.motel.api.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.kltn.motel.api.admin.payload.DiscountDatasource;
import com.server.kltn.motel.service.DiscountManagementService;

@RestController
@RequestMapping(value = {"/api"})
public class DiscountManagement {
	
	@Autowired
	private DiscountManagementService discountManagementService;
	
	@PostMapping("/discount-management-create")
	public ResponseEntity<DiscountDatasource> createDiscount(@RequestBody DiscountDatasource discountDatasource){
		DiscountDatasource discount= discountManagementService.createDiscount(discountDatasource);
		return new ResponseEntity<DiscountDatasource>(discount, HttpStatus.OK);
	}
}
