package com.server.kltn.motel.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.kltn.motel.api.admin.payload.DiscountDatasource;
import com.server.kltn.motel.api.admin.payload.ExpenseDatasource;
import com.server.kltn.motel.common.HandleDateCommon;
import com.server.kltn.motel.constant.CodeConstant;
import com.server.kltn.motel.entity.Discount;
import com.server.kltn.motel.entity.Expense;
import com.server.kltn.motel.mapper.DiscountMapper;
import com.server.kltn.motel.repository.DiscountRepository;
import com.server.kltn.motel.repository.ExpenseRepository;
import com.server.kltn.motel.service.DiscountManagementService;

@Service
public class DiscountManagementImpl implements DiscountManagementService {

	@Autowired
	private DiscountRepository discountRepository;

	@Autowired
	private ExpenseRepository expenseRepository;

	@Autowired
	private DiscountMapper discountMapper;

	@Autowired
	private HandleDateCommon handleDateCommon;

	@Override
	public String generatedCodeDiscount() {
		// create random string builder
		StringBuilder sb = new StringBuilder();
		boolean check = true;
		while (check) {
			// combine all strings
			String alphaNumeric = CodeConstant.UpperAlphabet + CodeConstant.LowerAlphabet + CodeConstant.Numbers;

			// create an object of Random class
			Random random = new Random();

			for (int i = 0; i < CodeConstant.SIZE; i++) {
				// generate random index number
				int index = random.nextInt(alphaNumeric.length());

				// get character specified by index
				// from the string
				char randomChar = alphaNumeric.charAt(index);

				// append the character to string builder
				sb.append(randomChar);
			}
			if (!discountRepository.checkGeneratedCodeExists(sb.toString())) {
				check = false;
			}
		}

		return sb.toString();
	}

	@Override
	@Transactional
	public DiscountDatasource createDiscount(DiscountDatasource discountDatasource) {
		discountDatasource.setCode(this.generatedCodeDiscount());
		Discount discount = discountMapper.mapDiscountDatasourceToDiscount(discountDatasource);

		List<Long> ids = new ArrayList<>();
		for (ExpenseDatasource expenseDatasource : discountDatasource.getExpenseDatasources()) {
			ids.add(expenseDatasource.getId());
		}
		List<Expense> expenses = expenseRepository.findByIdIn(ids);
		discount.setExpenses(expenses);
		Discount newDiscount = discountRepository.save(discount);
		return discountMapper.mapDiscountToDiscountDatasource(newDiscount);
	}

	@Override
	public List<DiscountDatasource> getDiscountOfExpense(Long expenseId) {
		List<Expense> expenses = expenseRepository.findByIdIn(new ArrayList<Long>(Arrays.asList(expenseId)));
		List<DiscountDatasource> discountDatasources = new ArrayList<>();
		LocalDateTime currentDateTime = handleDateCommon.getCurrentDateTime();
		for (Discount discount : expenses.get(0).getDiscounts()) {
			if (discount.getEndDate().compareTo(currentDateTime) > 0) {
				discountDatasources.add(discountMapper.mapDiscountToDiscountDatasource(discount));
			}
		}
		return discountDatasources;
	}

	@Override
	public List<DiscountDatasource> getAllDiscount() {
		List<Discount> discounts = discountRepository.findAll();
		Collections.sort(discounts, new Comparator<Discount>() {
			@Override
			public int compare(Discount d1, Discount d2) {
				return d1.getStartedDate().compareTo(d2.getStartedDate());
			}
		});
		List<DiscountDatasource> discountDatasources = new ArrayList<>();
		LocalDateTime currentDateTime = handleDateCommon.getCurrentDateTime();
		for (Discount discount : discounts) {
			if (discount.getEndDate().compareTo(currentDateTime) > 0) {
				discountDatasources.add(discountMapper.mapDiscountToDiscountDatasource(discount));
			}
		}
		return discountDatasources;
	}
}
