package com.server.kltn.motel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.kltn.motel.api.admin.payload.ExpenseDatasource;
import com.server.kltn.motel.entity.Expense;
import com.server.kltn.motel.mapper.ExpenseMapper;
import com.server.kltn.motel.repository.ExpenseRepository;
import com.server.kltn.motel.service.ExpenseService;

@Service
public class ExpenseImpl implements ExpenseService{
	@Autowired
	private ExpenseRepository expenseRepository;
	
	@Autowired
	private ExpenseMapper expenseMapper;
	@Override
	public List<ExpenseDatasource> getExpenses() {
		List<Expense> expenses= expenseRepository.findAll();
		return expenseMapper.convertExpenseToExpenseDatasources(expenses);
	}
}
