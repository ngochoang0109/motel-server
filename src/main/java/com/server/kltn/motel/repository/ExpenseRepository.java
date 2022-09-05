package com.server.kltn.motel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.server.kltn.motel.entity.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long>{
	List<Expense> findByIdIn(List<Long> ids);
}
