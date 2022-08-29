package com.server.kltn.motel.mapper;

import org.springframework.stereotype.Component;

import com.server.kltn.motel.api.admin.payload.ExpenseDatasource;
import com.server.kltn.motel.entity.Expense;

@Component
public class ExpenseMapper {
	public Expense convertExpenseDatasourceToExpense(ExpenseDatasource expenseDatasource) {
		return new Expense(expenseDatasource.getId(), expenseDatasource.getType(), expenseDatasource.getCost());
	}
}
