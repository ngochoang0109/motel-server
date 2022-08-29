package com.server.kltn.motel.service;

import com.server.kltn.motel.api.admin.payload.DiscountDatasource;
import com.server.kltn.motel.entity.Discount;

public interface DiscountManagementService {
	String generatedCodeDiscount();
	Discount createDiscount(DiscountDatasource discountDatasource);
}
