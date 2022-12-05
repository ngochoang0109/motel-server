package com.server.kltn.motel.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.server.kltn.motel.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsernameOrEmail(String username, String email);
	Boolean existsByUsername(String email);
    Boolean existsByEmail(String email);
}
