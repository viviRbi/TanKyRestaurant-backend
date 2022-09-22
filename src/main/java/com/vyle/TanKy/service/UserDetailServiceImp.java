package com.vyle.TanKy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.vyle.TanKy.model.AppUser;
import com.vyle.TanKy.repository.UserRepository;

public class UserDetailServiceImp implements UserDetailService{

	@Autowired
	UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user = userRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User "+ username + "Not found"));
		return UserDetailsImpl.build(user);
	}

}
