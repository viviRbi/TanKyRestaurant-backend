package com.vyle.TanKy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vyle.TanKy.model.Dish;

@Repository
public interface DishRepository extends JpaRepository<Dish, Integer>{

}
