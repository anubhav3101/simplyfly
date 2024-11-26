package com.simplyfly.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.simplyfly.model.user;

@Repository
public interface UserRepository extends JpaRepository<user, Long> {

    // Find user by email
    Optional<user> findByEmail(String email);
    
    Optional<user> findByName(String name);

    

    // Native query to count total users
    @Query(value = "SELECT COUNT(*) FROM user", nativeQuery = true)
    Long countTotalUsers();
    
	Boolean existsByEmail(String email);

	Optional<user> findBynameOrEmail(String name, String email);

	boolean existsByUsername(String username);

	

}