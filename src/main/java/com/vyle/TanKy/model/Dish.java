package com.vyle.TanKy.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;


@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NoArgsConstructor
@AllArgsConstructor
@Builder @Getter @Setter @ToString
// lombok @Data not working (causes @NoArgsConstructor @AllArgsConstructor not working)
public class Dish {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dish_id;
	
	@NonNull
	private String name;
	
    @JsonBackReference
	@ManyToOne
    @JoinColumn(name="category_id")
	private Category category;
	
 	@CreationTimestamp
 	private Date created_on;
 	
 	@UpdateTimestamp
 	private Date updated_on;
	
}
