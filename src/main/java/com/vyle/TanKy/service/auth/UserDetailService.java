package com.vyle.TanKy.service.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailService {

	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
