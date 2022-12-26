package com.server.kltn.motel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.server.kltn.motel.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{
	
	@Query("SELECT p "
			+ "FROM Payment p "
			+ "WHERE p.id= :id")
	Payment getPaymentDetail(@Param("id") long id);
	
	@Query("SELECT p "
			+ "FROM Payment p "
			+ "WHERE "
//			+ "p.status != 0 "
//			+ " AND "
			+ "p.user.username= :username")
	List<Payment> getPaymentHistory(@Param("username") String username);
}
