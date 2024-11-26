package com.simplyfly.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simplyfly.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(String name);
	boolean existsByName(String name); 

}