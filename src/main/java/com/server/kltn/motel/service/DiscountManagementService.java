package com.server.kltn.motel.service;

import java.util.List;

import com.server.kltn.motel.api.admin.payload.DiscountDatasource;

public interface DiscountManagementService {
	String generatedCodeDiscount();
	DiscountDatasource createDiscount(DiscountDatasource discountDatasource);
	List<DiscountDatasource> getDiscountOfExpense(Long expenseId);
	List<DiscountDatasource> getAllDiscount();
}
