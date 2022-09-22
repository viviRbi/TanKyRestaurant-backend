package com.vyle.TanKy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vyle.TanKy.model.AppUser;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Integer>{
	
	Optional<AppUser> findByUsername(String username);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);
}
