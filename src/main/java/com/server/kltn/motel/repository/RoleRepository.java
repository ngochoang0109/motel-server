package com.server.kltn.motel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.server.kltn.motel.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

}
