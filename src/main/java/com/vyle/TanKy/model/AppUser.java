package com.vyle.TanKy.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email")
})
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor @RequiredArgsConstructor
public class AppUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank
	@Size(max=20)
	@NonNull
	private String username;
	
	@NotBlank
	@Size(max=20)
	@Email
	@NonNull
	private String email;
	
	@NotBlank
	@Size(max=120)
	@NonNull
	private String password;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "appuser_role",
		joinColumns = @JoinColumn(name="user_id"),
		inverseJoinColumns = @JoinColumn(name="role_id")
	)	
	private Set<Role> roles = new HashSet<>();
		
}
