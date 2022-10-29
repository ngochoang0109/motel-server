package com.server.kltn.motel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.server.kltn.motel.entity.Discount;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long>{
	@Query("select count(d)>0 from Discount d where code= :code")
	boolean checkGeneratedCodeExists( @Param("code") String code);
	
	@Query("select d from Discount d where code= :code")
	Discount getByCode( @Param("code") String code);
}