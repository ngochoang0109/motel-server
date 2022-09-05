package com.server.kltn.motel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.server.kltn.motel.entity.TypeOfAcc;

@Repository
public interface TypeOfAccRepository extends JpaRepository<TypeOfAcc, Long>{
	@Query("select a from TypeOfAcc a")
	List<TypeOfAcc> getAll();
	List<TypeOfAcc> findByIdIn(List<Long> ids);
}
