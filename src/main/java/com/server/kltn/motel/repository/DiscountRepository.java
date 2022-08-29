package com.server.kltn.motel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.kltn.motel.entity.Discount;

public interface DiscountRepository extends JpaRepository<Discount, Long>{
	@Query("select count(d)>0 from Discount d where code= :code")
	boolean checkGeneratedCodeExists( @Param("code") String code);
}
