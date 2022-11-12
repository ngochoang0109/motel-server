//package com.server.kltn.motel.repository;
//
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//import com.server.kltn.motel.entity.Cart;
//
//@Repository
//public interface PaymentRepo extends JpaRepository<Cart, Long>{
//	@Query("SELECT p"
//    		+ " FROM Cart c INNER JOIN c.user u"
//    		+ " Where c.delFlag=false"
//    		+ " and u.username=:username")
//    Optional<Cart> getCartIsPayingByUser(@Param("username") String username);
//	
//	@Query("SELECT p "
//			+ "FROM Cart c "
//			+ "WHERE c.id= :idCart ")
//	Optional<Cart> getCartById(@Param("idCart") long idCart);
//}
