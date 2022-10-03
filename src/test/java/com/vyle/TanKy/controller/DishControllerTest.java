package com.vyle.TanKy.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import com.vyle.TanKy.model.Category;
import com.vyle.TanKy.model.Dish;
import com.vyle.TanKy.repository.DishRepository;

@ExtendWith(MockitoExtension.class)
public class DishControllerTest {

	@InjectMocks
	DishController dishController;
	
	@Mock
	private DishRepository dishRepo;

	@Test
	public void testFindAllDish() throws Exception{
		Category mainDish = new Category(1,"Main Dish");
		Dish dishA = new  Dish(1,"Grilled fish", mainDish);
		Dish dishB = new  Dish(2,"Tandoori Chicken", mainDish);
		List<Dish> dishList = new ArrayList<Dish>();
		dishList.add(dishA); dishList.add(dishB);
		
		when(dishRepo.findAll()).thenReturn(dishList);
		
		List<Dish> result = dishRepo.findAll();
		
		assertEquals(dishList, result);
		
	}

	@Test
	public void testFindDishById() throws Exception{
		
		Category mainDish = new Category(1,"Main Dish");
		
		Optional<Dish> dishA =Optional.ofNullable(new Dish(1,"Grilled fish", mainDish));		
		
		when(dishRepo.findById(1)).thenReturn(dishA);
		
		Optional<Dish> result = dishRepo.findById(1);
		
		assertEquals(dishA, result);
		
	}

	
	
}
