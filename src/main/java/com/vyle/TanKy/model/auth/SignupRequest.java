package com.vyle.TanKy.model.auth;

import java.util.Set;

import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class SignupRequest {

	@NotBlank
	@Size(min=3, max=20)
	private String username;
	
	@NotBlank
	@Size(min=5, max=50)
	private String email;
	
	private Set<String> role;
	
	@NotBlank
	@Size(min=5, max=50)
	private String password;
}
