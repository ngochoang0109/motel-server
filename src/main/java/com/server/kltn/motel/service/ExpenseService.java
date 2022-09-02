package com.server.kltn.motel.service;

import java.util.List;

import com.server.kltn.motel.api.admin.payload.ExpenseDatasource;

public interface ExpenseService {
	List<ExpenseDatasource> getExpenses();
}
