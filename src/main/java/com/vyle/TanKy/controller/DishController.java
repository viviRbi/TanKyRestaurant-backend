package com.vyle.TanKy.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vyle.TanKy.model.Dish;
import com.vyle.TanKy.repository.DishRepository;

@RestController
@CrossOrigin(origins = "${app.origin}")
public class DishController {

		@Autowired
		private DishRepository dishRepo;
		
		@GetMapping("/dish/all")
		public List<Dish> getAllDish(){
			List<Dish> dishes = dishRepo.findAll();
			return dishes;
		}
		
		@GetMapping("/dish/{id}")
		public Optional<Dish> getDishById(@PathVariable Integer id){
			Optional<Dish> d = dishRepo.findById(id);
			return d;
		}
		@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')")
		@PostMapping("/dish/add")
		public String insertDish(@RequestBody Dish dish) {
			Dish saved = dishRepo.save(dish);
			if (saved != null) return "Saved. Dish id is " + dish.toString();
			else return "Fail to save";
		}
}
