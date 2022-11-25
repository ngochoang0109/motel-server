package com.server.kltn.motel.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.server.kltn.motel.api.user.payload.CountNews;
import com.server.kltn.motel.api.user.payload.paymentDetail.CountRelatedNews;
import com.server.kltn.motel.api.user.payload.paymentDetail.HightExpenseRelated;
import com.server.kltn.motel.entity.Accomodation;

@Repository
public interface AccomodationRepo extends JpaRepository<Accomodation, Long> {
	@Query("select new com.server.kltn.motel.api.user.payload.CountNews(COUNT(a.province),a.province)"
			+ " from Accomodation a" + " where a.post.startedDate <= :currentDate and"
			+ " :currentDate < a.post.closedDate and" + "	a.post.isPayment = true and" + "	a.post.status=1"
			+ " group by a.province")
	List<CountNews> countByProvince(@Param("currentDate") LocalDateTime currentDate);

	@Query("SELECT new com.server.kltn.motel.api.user.payload.paymentDetail.CountRelatedNews(COUNT(a.ward),a.ward)"
			+ " FROM Accomodation a" + " WHERE a.post.status=1 and" + " a.post.isPayment=true and"
			+ " a.post.startedDate <=:currentDate and" + " a.post.closedDate  >= :currentDate and"
			+ " a.typeOfAcc.name= :type and" + " a.dicstrict= :district " + " GROUP BY a.ward")
	List<CountRelatedNews> getReleatedNewsOfTypeAndDistrict(@Param("type") String type,
			@Param("district") String district, @Param("currentDate") LocalDateTime currentDate);

	@Query("SELECT new com.server.kltn.motel.api.user.payload.paymentDetail.HightExpenseRelated(a.typeOfAcc.name,a.ward) " 
			+ "	FROM Accomodation a" 
			+ "	WHERE a.post.status=1 and"
			+ "			a.post.isPayment=true and" 
			+ "			a.post.startedDate <=:currentDate and"
			+ "			a.post.closedDate  >= :currentDate and"
			+ "			a.post.expense.id IN (:arrExpenseId)"
			+ " GROUP BY a.ward"
			+ " ORDER BY a.post.expense.cost desc"
	)
	List<HightExpenseRelated> getPostHighExpense(@Param("currentDate") LocalDateTime currentDate, 
			@Param("arrExpenseId") List<Long> arrExpenseId);
	
	@Query("SELECT a"
			+ " FROM Accomodation a" 
			+ " WHERE a.post.status=1 and" 
			+ " a.post.isPayment=true and"
			+ " a.post.startedDate <=:currentDate and" 
			+ " a.post.closedDate  >= :currentDate and"
			+ " a.dicstrict= :district and"
			+ " a.province = :province")
	List<Accomodation> getRelatedNews(@Param("province") String province,
			@Param("district") String district, 
			@Param("currentDate") LocalDateTime currentDate);
}