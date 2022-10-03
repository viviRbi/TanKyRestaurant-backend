package com.vyle.TanKy.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder @Getter @Setter @ToString
//lombok @Data not working (causes @NoArgsConstructor @AllArgsConstructor not working)
public class Category extends RepresentationModel<Category>{

	@NonNull
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer category_id;
	
	@NonNull
	private String name;
	 
	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY,mappedBy="category", cascade = CascadeType.ALL)
	private Set<Dish> dishes = new HashSet<>();
 
 	@CreationTimestamp
 	private Date created_on;
 	
 	@UpdateTimestamp
 	private Date updated_on;
 	
}
