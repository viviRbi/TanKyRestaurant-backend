
 package com.vyle.TanKy.service.auth;
 
 import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired; import
 org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import
 org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vyle.TanKy.model.AppUser; import
 com.vyle.TanKy.repository.UserRepository;
 
 @Service
 public class UserDetailsServiceImp implements UserDetailsService{
 
	@Autowired UserRepository userRepo;
 
	// search database to find User and use UserDetailService to build user
	// So that the user is now of UserDetailsService type
 	@Override 
 	@Transactional
 	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 
		 AppUser user = userRepo.findByUsername(username)
				 .orElseThrow(() -> new UsernameNotFoundException("User "+ username +"Not found")); 
		 return UserDetailsImpl.build(user); 
 	}
 
 
}
