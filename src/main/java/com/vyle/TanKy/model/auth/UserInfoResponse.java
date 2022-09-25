package com.vyle.TanKy.model.auth;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @AllArgsConstructor
public class UserInfoResponse {
	private Integer id;
	private String username;
	private String email;
	private List<String> roles;
}
