package com.server.kltn.motel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.server.kltn.motel.entity.Payment;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long>{
	@Query("SELECT p"
    		+ " FROM Payment p INNER JOIN p.user u"
    		+ " Where p.delFlag=false"
    		+ " and u.username=:username")
    Optional<Payment> getCartIsPayingByUser(@Param("username") String username);
}
