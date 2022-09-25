package com.vyle.TanKy.model.auth;

 import javax.validation.constraints.*;

import lombok.Data;

@Data
public class LoginRequest {
	
	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
}
