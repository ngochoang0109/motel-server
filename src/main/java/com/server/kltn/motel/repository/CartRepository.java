package com.server.kltn.motel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.server.kltn.motel.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
	@Query("SELECT c"
    		+ " FROM Cart c INNER JOIN c.user u"
    		+ " Where u.username=:username")
    Optional<Cart> getCartOfUser(@Param("username") String username);
}
