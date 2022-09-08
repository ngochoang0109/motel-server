package com.server.kltn.motel.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.server.kltn.motel.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
	@Query("SELECT p "
			+ "FROM Post p "
			+ "WHERE p.startedDate< :currentDate and"
			+ "	:currentDate< p.closedDate and"
			+ " p.isPayment = true and"
			+ " p.status=1")
	Page<Post> getPosts(Pageable pageable, @Param("currentDate") LocalDateTime currentDate);
	
	@Query("SELECT p"
			+ " FROM Post p"
			+ " WHERE p.user.username=:username")
	Page<Post> getNewsOfUser(Pageable pageable, @Param("username") String username);
	
	@Query("SELECT p"
			+ " FROM Post p"
			+ " WHERE p.user.username=:username and"
			+ " p.status=0")
	Page<Post> getNewsWaittingAprovedOfUser(Pageable pageable, @Param("username") String username);
	
	@Query("SELECT p"
			+ " FROM Post p"
			+ " WHERE p.user.username=:username and"
			+ " p.status=2")
	Page<Post> getNewsRejectOfUser(Pageable pageable, @Param("username") String username);
	
	@Query("SELECT p"
			+ " FROM Post p"
			+ " WHERE p.user.username=:username and"
			+ " p.status=1 and"
			+ " p.isPayment=false")
	Page<Post> getDontPaymentOfUser(Pageable pageable, @Param("username") String username);
	
	@Query("SELECT p"
			+ " FROM Post p"
			+ " WHERE p.user.username=:username and"
			+ " p.status=1 and"
			+ " p.isPayment=true and"
			+ " p.startedDate>:currentDate")
	Page<Post> getWaittingShowOfUser(Pageable pageable, @Param("username") String username, 
										@Param("currentDate") LocalDateTime currentDate);
	
	@Query("SELECT p"
			+ " FROM Post p"
			+ " WHERE p.user.username=:username and"
			+ " p.status=1 and"
			+ " p.isPayment=true and"
			+ " p.startedDate<=:currentDate and"
			+ " p.closedDate >= :currentDate")
	Page<Post> getNewsShowingOfUser(Pageable pageable, @Param("username") String username, 
										@Param("currentDate") LocalDateTime currentDate);
	
	@Query("SELECT p"
			+ " FROM Post p"
			+ " WHERE p.user.username=:username and"
			+ " p.status=1 and"
			+ " p.isPayment=true and"
			+ " p.closedDate < :currentDate")
	Page<Post> getNewsExpriedOfUser(Pageable pageable, @Param("username") String username, 
										@Param("currentDate") LocalDateTime currentDate);
	
	@Query("SELECT p"
			+ " FROM Post p"
			+ " WHERE p.user.username=:username and"
			+ " p.status=3")
	Page<Post> getNewsHiddenOfUser(Pageable pageable, @Param("username") String username);
}
