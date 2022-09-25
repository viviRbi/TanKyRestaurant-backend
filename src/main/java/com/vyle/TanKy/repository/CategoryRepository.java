package com.vyle.TanKy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import com.vyle.TanKy.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
