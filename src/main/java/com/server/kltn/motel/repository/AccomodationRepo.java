package com.server.kltn.motel.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.server.kltn.motel.api.user.payload.CountNews;
import com.server.kltn.motel.entity.Accomodation;

@Repository
public interface AccomodationRepo extends JpaRepository<Accomodation, Long>{
	@Query("select new com.server.kltn.motel.api.user.payload.CountNews(COUNT(a.province),a.province)"
			+ " from Accomodation a"
			+ " where a.post.startedDate <= :currentDate and"
			+ " :currentDate< a.post.closedDate and"
			+ "	a.post.isPayment = true and"
			+ "	a.post.status=1"
			+ " group by a.province")
	List<CountNews> countByProvince(@Param("currentDate") LocalDateTime currentDate);
}