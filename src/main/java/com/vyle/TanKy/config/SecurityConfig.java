package com.vyle.TanKy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception{
		return http.csrf().disable() // disable cross site request forgery
		.authorizeRequests(auth -> {
			auth.antMatchers("/").permitAll();
			auth.antMatchers("/category/add","/dish/add").hasRole("ADMIN");
		})
		.httpBasic(Customizer.withDefaults())
		.build();
	}
	
//	@Bean 
//	JwtDecoder jwtDecoder() {
//		return NimbusJwtDecoder.w
//	}
}
