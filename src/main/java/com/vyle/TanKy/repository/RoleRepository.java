package com.vyle.TanKy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vyle.TanKy.model.EnumRole;
import com.vyle.TanKy.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
	Optional<Role> findByName(EnumRole name);
}
