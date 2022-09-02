package com.server.kltn.motel.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.server.kltn.motel.api.admin.payload.ExpenseDatasource;
import com.server.kltn.motel.entity.Expense;

@Component
public class ExpenseMapper {
	public Expense convertExpenseDatasourceToExpense(ExpenseDatasource expenseDatasource) {
		return new Expense(expenseDatasource.getId(), expenseDatasource.getType(), expenseDatasource.getCost());
	}
	
	public ExpenseDatasource convertExpenseToExpenseDatasource(Expense expense) {
		return new ExpenseDatasource(expense.getId(), expense.getType(), expense.getCost());
	}
	
	public List<ExpenseDatasource> convertExpenseToExpenseDatasources(List<Expense> expenses){
		List<ExpenseDatasource> expenseDatasources= new ArrayList<>();
		for (Expense expense : expenses) {
			expenseDatasources.add(this.convertExpenseToExpenseDatasource(expense));
		}
		return expenseDatasources;
	}
}
