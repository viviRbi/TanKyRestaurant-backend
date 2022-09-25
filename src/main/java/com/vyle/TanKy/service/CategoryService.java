package com.vyle.TanKy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;

import com.vyle.TanKy.controller.CategoryController;
import com.vyle.TanKy.model.Category;
import com.vyle.TanKy.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	CategoryRepository cateRepo;
	
	@Secured("ROLE_ADMIN")
	public List<Category> getAllCategory(){
		 List<Category> allCate = cateRepo.findAll();	
		 return allCate;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	public Category getCategory(@PathVariable Integer id) throws Exception {
		Category c = cateRepo.findById(id).orElseThrow(() -> new Exception("Not found category with id "+ id));
		Link withRel = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoryController.class)
			.getAllCategory())
			.withRel("All-Category");
		c.add(withRel);
		return c; 
	}
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String insertCategory(@RequestBody Category category) {
		Category saved = cateRepo.save(category);
		if (saved != null) return "Saved. Category is " + category.toString();
		else return "Fail to save";
	}

}
