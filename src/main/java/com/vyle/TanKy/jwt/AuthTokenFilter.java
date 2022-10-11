package com.vyle.TanKy.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.vyle.TanKy.aop.LoggingAspect;
import com.vyle.TanKy.service.auth.UserDetailsServiceImp;
import com.vyle.TanKy.util.JwtUtils;

public class AuthTokenFilter extends OncePerRequestFilter{
	
	private final Logger logger = LogManager.getLogger(LoggingAspect.class);
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private UserDetailsServiceImp userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// Get jwt from Http Cookies and parse username from it
		try {
			String jwt = jwtUtils.getJwtCookies(request);
			if(jwt != null && jwtUtils.validateJwtToken(jwt)) {
				String username = jwtUtils.getUserNameFromJwtToken(jwt);
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);

				UsernamePasswordAuthenticationToken authentication = 
						new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} 
		}catch(Exception e) {
			logger.error("Cannot set authentication: {} " + e.getMessage());
		}
		filterChain.doFilter(request, response);
	}

}
