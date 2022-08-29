package com.server.kltn.motel.mapper;

import org.springframework.stereotype.Component;

import com.server.kltn.motel.api.admin.payload.DiscountDatasource;
import com.server.kltn.motel.common.HandleDateCommon;
import com.server.kltn.motel.entity.Discount;

@Component
public class DiscountMapper {
	HandleDateCommon handleDateCommon= new HandleDateCommon();
	public Discount mapDiscountDatasourceToDiscount(DiscountDatasource discountDatasource) {
		Discount discount= new Discount();
		discount.setStartedDate(handleDateCommon.convertStringDateToLocalDateTime( discountDatasource.getStartedDate()));
		discount.setEndDate(handleDateCommon.convertStringDateToLocalDateTime( discountDatasource.getEndDate()));
		discount.setPercent(discountDatasource.getPercent());
		discount.setPrice(discountDatasource.getPrice());
		discount.setCode(discountDatasource.getCode());
		return discount;
	}
}
