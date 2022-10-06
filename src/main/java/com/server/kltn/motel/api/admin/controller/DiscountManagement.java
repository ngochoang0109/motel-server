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
import org.springframework.web.bind.annotation.RestController;

import com.server.kltn.motel.api.admin.payload.DiscountDatasource;
import com.server.kltn.motel.api.admin.payload.ExpenseDatasource;
import com.server.kltn.motel.service.DiscountManagementService;
import com.server.kltn.motel.service.ExpenseService;

@RestController
@RequestMapping(value = {"/api"})
public class DiscountManagement {
	
	@Autowired
	private DiscountManagementService discountManagementService;
	
	@Autowired
	private ExpenseService expenseService;
	
	
	@PostMapping("/discount-management-create")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<DiscountDatasource> createDiscount(@RequestBody DiscountDatasource discountDatasource){
		DiscountDatasource discount= discountManagementService.createDiscount(discountDatasource);
		return new ResponseEntity<DiscountDatasource>(discount, HttpStatus.OK);
	}
	
	@GetMapping("/discount-management-get-all")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<DiscountDatasource>> getAllDiscount(){
		List<DiscountDatasource> discounts= discountManagementService.getAllDiscount();
		return new ResponseEntity<List<DiscountDatasource>>(discounts, HttpStatus.OK);
	}
	
	@GetMapping("/discount-management-get-expense")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<ExpenseDatasource>> getExpenses(){
		List<ExpenseDatasource> expenseDatasources= expenseService.getExpenses();
		return new ResponseEntity<List<ExpenseDatasource>>(expenseDatasources, HttpStatus.OK);
	}
}
