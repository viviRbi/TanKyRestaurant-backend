package com.vyle.TanKy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig{

	public SecurityFilterChain configure(HttpSecurity http) throws Exception{
		return http.csrf().disable() // disable cross site request forgery
		.authorizeRequests()
		.antMatchers("/category/add","/dish/add").hasRole("ADMIN")
		.anyRequest().authenticated()
		.and().httpBasic()
		.and().build();
	}
}
