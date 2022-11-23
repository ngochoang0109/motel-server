package com.server.kltn.motel.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.kltn.motel.entity.CartDetail;

public interface CartDetailRepository extends JpaRepository<CartDetail, Long>{
	
	@Query("SELECT cd "
			+ "FROM CartDetail cd "
			+ "WHERE cd.cart.id= :cartId "
			+ "and cd.post.id= :postId")
	CartDetail getPostOfCart(@Param("cartId") long cartId, @Param("postId") long postId);
	
	@Modifying(clearAutomatically = true)
	@Query("DELETE CartDetail cd "
			+ "WHERE cd.cart.id= :cartId "
			+ "and cd.post.id= :postId")
	int deleteCartDetail(@Param("cartId") long cartId, @Param("postId") long postId);
}
