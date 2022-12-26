package com.server.kltn.motel.repository;

import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.server.kltn.motel.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
	@Query("SELECT p " + "FROM Post p " + "WHERE p.startedDate<= :currentDate and" + "	:currentDate< p.closedDate and"
			+ " p.isPayment = true and" + " p.status=1")
	Page<Post> getPosts(Pageable pageable, @Param("currentDate") LocalDateTime currentDate);

	@Query("SELECT p " + "FROM Post p " + "WHERE p.startedDate< :currentDate and" + "	:currentDate< p.closedDate and"
			+ " p.isPayment = true and" + " p.status=1 and" + " p.accomodation.typeOfAcc.id=:type")
	Page<Post> getPostsByType(Pageable pageable, @Param("currentDate") LocalDateTime currentDate,
			@Param("type") long type);

	@Query("SELECT p" 
			+ " FROM Post p" 
			+ " WHERE p.user.username=:username and" 
			+ " p.status != 4 and"
			+ " (:textSearch is null or p.title like %:textSearch% ) and"
			+ "	(:startedDate is null or p.startedDate >= :startedDate) and"
			+ "	(:closedDate is null or p.closedDate <= :closedDate) and"
			+ "	(:typeOfAcc is null or p.accomodation.typeOfAcc.id = :typeOfAcc) and"
			+ "	(:typeOfNews is null or p.expense.id = :typeOfNews) and"
			+ "	(:province is null or p.accomodation.province = :province) and"
			+ "	(:district is null or p.accomodation.dicstrict = :district)")
	Page<Post> getNewsOfUser(Pageable pageable, @Param("username") String username,
			@Param("textSearch") String textSearch, @Param("startedDate") LocalDateTime startedDate,
			@Param("closedDate") LocalDateTime closedDate, @Param("typeOfAcc") Long typeOfAcc,
			@Param("typeOfNews") Long typeOfNews, @Param("province") String province,
			@Param("district") String district);

	@Query("SELECT p" 
			+ " FROM Post p" 
			+ " WHERE p.user.username=:username and" 
			+ " p.status=0 and"
			+ " (:textSearch is null or p.title like %:textSearch% ) and"
			+ "	(:startedDate is null or p.startedDate >= :startedDate) and"
			+ "	(:closedDate is null or p.closedDate <= :closedDate) and"
			+ "	(:typeOfAcc is null or p.accomodation.typeOfAcc.id = :typeOfAcc) and"
			+ "	(:typeOfNews is null or p.expense.id = :typeOfNews) and"
			+ "	(:province is null or p.accomodation.province = :province) and"
			+ "	(:district is null or p.accomodation.dicstrict = :district)")
	Page<Post> getNewsWaittingAprovedOfUser(Pageable pageable, @Param("username") String username,
			@Param("textSearch") String textSearch, @Param("startedDate") LocalDateTime startedDate,
			@Param("closedDate") LocalDateTime closedDate, @Param("typeOfAcc") Long typeOfAcc,
			@Param("typeOfNews") Long typeOfNews, @Param("province") String province,
			@Param("district") String district);

	@Query("SELECT p" + " FROM Post p" + " WHERE p.user.username=:username and" + " p.status=2 and"
			+ " (:textSearch is null or p.title like %:textSearch% ) and"
			+ "	(:startedDate is null or p.startedDate >= :startedDate) and"
			+ "	(:closedDate is null or p.closedDate <= :closedDate) and"
			+ "	(:typeOfAcc is null or p.accomodation.typeOfAcc.id = :typeOfAcc) and"
			+ "	(:typeOfNews is null or p.expense.id = :typeOfNews) and"
			+ "	(:province is null or p.accomodation.province = :province) and"
			+ "	(:district is null or p.accomodation.dicstrict = :district)")
	Page<Post> getNewsRejectOfUser(Pageable pageable, @Param("username") String username,
			@Param("textSearch") String textSearch, @Param("startedDate") LocalDateTime startedDate,
			@Param("closedDate") LocalDateTime closedDate, @Param("typeOfAcc") Long typeOfAcc,
			@Param("typeOfNews") Long typeOfNews, @Param("province") String province,
			@Param("district") String district);

	@Query("SELECT p" 
			+ " FROM Post p" 
			+ " WHERE p.user.username=:username and" 
			+ " p.status=1 and"
			+ " p.isPayment=false and"
			+ " (:textSearch is null or p.title like %:textSearch% ) and"
			+ "	(:startedDate is null or p.startedDate >= :startedDate) and"
			+ "	(:closedDate is null or p.closedDate <= :closedDate) and"
			+ "	(:typeOfAcc is null or p.accomodation.typeOfAcc.id = :typeOfAcc) and"
			+ "	(:typeOfNews is null or p.expense.id = :typeOfNews) and"
			+ "	(:province is null or p.accomodation.province = :province) and"
			+ "	(:district is null or p.accomodation.dicstrict = :district)")
	Page<Post> getDontPaymentOfUser(Pageable pageable, @Param("username") String username,
			@Param("textSearch") String textSearch, @Param("startedDate") LocalDateTime startedDate,
			@Param("closedDate") LocalDateTime closedDate, @Param("typeOfAcc") Long typeOfAcc,
			@Param("typeOfNews") Long typeOfNews, @Param("province") String province,
			@Param("district") String district);

	@Query("SELECT p" 
			+ " FROM Post p" 
			+ " WHERE p.user.username=:username and" 
			+ " p.status=1 and"
			+ " p.isPayment=true and" 
			+ " p.startedDate>:currentDate and"
			+ " (:textSearch is null or p.title like %:textSearch% ) and"
			+ "	(:startedDate is null or p.startedDate >= :startedDate) and"
			+ "	(:closedDate is null or p.closedDate <= :closedDate) and"
			+ "	(:typeOfAcc is null or p.accomodation.typeOfAcc.id = :typeOfAcc) and"
			+ "	(:typeOfNews is null or p.expense.id = :typeOfNews) and"
			+ "	(:province is null or p.accomodation.province = :province) and"
			+ "	(:district is null or p.accomodation.dicstrict = :district)")
	Page<Post> getWaittingShowOfUser(Pageable pageable, @Param("username") String username,
			@Param("currentDate") LocalDateTime currentDate, @Param("textSearch") String textSearch, @Param("startedDate") LocalDateTime startedDate,
			@Param("closedDate") LocalDateTime closedDate, @Param("typeOfAcc") Long typeOfAcc,
			@Param("typeOfNews") Long typeOfNews, @Param("province") String province,
			@Param("district") String district);

	@Query("SELECT p" 
			+ " FROM Post p" 
			+ " WHERE p.user.username=:username and" 
			+ " p.status=1 and"
			+ " p.isPayment=true and" 
			+ " p.startedDate<=:currentDate and" 
			+ " p.closedDate >= :currentDate and"
			+ " (:textSearch is null or p.title like %:textSearch% ) and"
			+ "	(:startedDate is null or p.startedDate >= :startedDate) and"
			+ "	(:closedDate is null or p.closedDate <= :closedDate) and"
			+ "	(:typeOfAcc is null or p.accomodation.typeOfAcc.id = :typeOfAcc) and"
			+ "	(:typeOfNews is null or p.expense.id = :typeOfNews) and"
			+ "	(:province is null or p.accomodation.province = :province) and"
			+ "	(:district is null or p.accomodation.dicstrict = :district)")
	Page<Post> getNewsShowingOfUser(Pageable pageable, @Param("username") String username,
			@Param("currentDate") LocalDateTime currentDate, @Param("textSearch") String textSearch, @Param("startedDate") LocalDateTime startedDate,
			@Param("closedDate") LocalDateTime closedDate, @Param("typeOfAcc") Long typeOfAcc,
			@Param("typeOfNews") Long typeOfNews, @Param("province") String province,
			@Param("district") String district);

	@Query("SELECT p" 
			+ " FROM Post p" 
			+ " WHERE p.user.username=:username and" 
			+ " p.status=1 and"
			+ " p.isPayment=true and" 
			+ " p.closedDate < :currentDate and"
			+ " (:textSearch is null or p.title like %:textSearch% ) and"
			+ "	(:startedDate is null or p.startedDate >= :startedDate) and"
			+ "	(:closedDate is null or p.closedDate <= :closedDate) and"
			+ "	(:typeOfAcc is null or p.accomodation.typeOfAcc.id = :typeOfAcc) and"
			+ "	(:typeOfNews is null or p.expense.id = :typeOfNews) and"
			+ "	(:province is null or p.accomodation.province = :province) and"
			+ "	(:district is null or p.accomodation.dicstrict = :district)")
	Page<Post> getNewsExpriedOfUser(Pageable pageable, @Param("username") String username,
			@Param("currentDate") LocalDateTime currentDate, @Param("textSearch") String textSearch, @Param("startedDate") LocalDateTime startedDate,
			@Param("closedDate") LocalDateTime closedDate, @Param("typeOfAcc") Long typeOfAcc,
			@Param("typeOfNews") Long typeOfNews, @Param("province") String province,
			@Param("district") String district);

	@Query("SELECT p" 
			+ " FROM Post p" 
			+ " WHERE p.user.username=:username and" 
			+ " p.status=3 and"
			+ " (:textSearch is null or p.title like %:textSearch% ) and"
			+ "	(:startedDate is null or p.startedDate >= :startedDate) and"
			+ "	(:closedDate is null or p.closedDate <= :closedDate) and"
			+ "	(:typeOfAcc is null or p.accomodation.typeOfAcc.id = :typeOfAcc) and"
			+ "	(:typeOfNews is null or p.expense.id = :typeOfNews) and"
			+ "	(:province is null or p.accomodation.province = :province) and"
			+ "	(:district is null or p.accomodation.dicstrict = :district)")
	Page<Post> getNewsHiddenOfUser(Pageable pageable, @Param("username") String username,
			 	@Param("textSearch") String textSearch, @Param("startedDate") LocalDateTime startedDate,
				@Param("closedDate") LocalDateTime closedDate, @Param("typeOfAcc") Long typeOfAcc,
				@Param("typeOfNews") Long typeOfNews, @Param("province") String province,
				@Param("district") String district);

	@Query("SELECT p" + " FROM Post p" + " WHERE p.user.username=:username and"
			+ " (p.title like %:textSearch%  or p.id like %:textSearch% )")
	Page<Post> getNewsByTextSearch(Pageable pageable, @Param("username") String username,
			@Param("textSearch") String textSearch);

	@Query("SELECT p" + " FROM Post p" + " WHERE p.user.username=:username and" + " p.status=0 and"
			+ " (p.title like %:textSearch%  or p.id like %:textSearch% )")
	Page<Post> getNewsWaitApprovedByTextSearch(Pageable pageable, @Param("username") String username,
			@Param("textSearch") String textSearch);

	@Query("SELECT p" + " FROM Post p" + " WHERE p.user.username=:username and" + " p.status=2 and"
			+ " (p.title like %:textSearch%  or p.id like %:textSearch% )")
	Page<Post> getNewsRejectByTextSearch(Pageable pageable, @Param("username") String username,
			@Param("textSearch") String textSearch);

	@Query("SELECT p" + " FROM Post p" + " WHERE p.user.username=:username and" + " p.status=1 and"
			+ " p.isPayment=false and" + " (p.title like %:textSearch%  or p.id like %:textSearch% )")
	Page<Post> getNewsWaitPaymentByTextSearch(Pageable pageable, @Param("username") String username,
			@Param("textSearch") String textSearch);

	@Query("SELECT p" + " FROM Post p" + " WHERE p.user.username=:username and" + " p.status=1 and"
			+ " p.isPayment=true and" + " p.startedDate>:currentDate and"
			+ " (p.title like %:textSearch%  or p.id like %:textSearch% )")
	Page<Post> getNewsWaitShowByTextSearch(Pageable pageable, @Param("username") String username,
			@Param("textSearch") String textSearch, @Param("currentDate") LocalDateTime currentDate);

	@Query("SELECT p" + " FROM Post p" + " WHERE p.user.username=:username and" + " p.status=1 and"
			+ " p.isPayment=true and" + " p.startedDate<=:currentDate and" + " p.closedDate >= :currentDate and"
			+ " (p.title like %:textSearch%  or p.id like %:textSearch% )")
	Page<Post> getNewsShowByTextSearch(Pageable pageable, @Param("username") String username,
			@Param("textSearch") String textSearch, @Param("currentDate") LocalDateTime currentDate);

	@Query("SELECT p" + " FROM Post p" + " WHERE p.user.username=:username and" + " p.status=3 and"
			+ " (p.title like %:textSearch%  or p.id like %:textSearch% )")
	Page<Post> getNewsHiddenByTextSearch(Pageable pageable, @Param("username") String username,
			@Param("textSearch") String textSearch);

	@Query("SELECT p" + " FROM Post p" + " WHERE p.user.username=:username and" + " p.status=1 and"
			+ " p.isPayment=true and" + " p.closedDate < :currentDate and"
			+ " (p.title like %:textSearch%  or p.id like %:textSearch% )")
	Page<Post> getNewsExpriedByTextSearch(Pageable pageable, @Param("username") String username,
			@Param("textSearch") String textSearch, @Param("currentDate") LocalDateTime currentDate);

	@Modifying(clearAutomatically = true)
	@Query("UPDATE Post p " + "SET p.isPayment = true " + "WHERE p.id= :postId")
	void updateIsPayment(@Param("postId") long postId);
}
