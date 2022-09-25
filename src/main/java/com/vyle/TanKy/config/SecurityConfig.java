package com.vyle.TanKy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.vyle.TanKy.jwt.AuthEntryPointJwt;
import com.vyle.TanKy.jwt.AuthTokenFilter;
import com.vyle.TanKy.service.auth.UserDetailsServiceImp;



@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
// public class SecurityConfig extends GlobalMethodSecurityConfiguration
public class SecurityConfig{
	
	@Autowired
	UserDetailsServiceImp userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;
	
	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}
	
	// use DaoAuthenticationProvider to retrieve data from User DetailsService
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	@Bean AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception{
		return authConfig.getAuthenticationManager();
	}
	
	@Bean 
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.cors().and().csrf().disable()
				.authorizeRequests().antMatchers("/auth/*","/swagger*/**","/actuator/**").permitAll()
				.antMatchers("/category/*").authenticated()
				.anyRequest().permitAll()
				.and()
				.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
				
		
		http.authenticationProvider(authenticationProvider());
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
}
