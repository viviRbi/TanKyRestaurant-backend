package com.vyle.TanKy.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vyle.TanKy.model.Category;
import com.vyle.TanKy.model.Dish;
import com.vyle.TanKy.repository.CategoryRepository;

@RestController
public class CategoryController {

	@Autowired
	CategoryRepository cateRepo;
	
	@GetMapping("/category/all")
	public List<Category> getAllCategory(){
		return cateRepo.findAll();	
	}
	
	@PostMapping("/category/add")
	public String insertCategory(@RequestBody Category category) {
		Category saved = cateRepo.save(category);
		if (saved != null) return "Saved. Category is " + category.toString();
		else return "Fail to save";
	}
}
