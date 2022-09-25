package com.vyle.TanKy.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vyle.TanKy.model.AppUser;
import com.vyle.TanKy.model.EnumRole;
import com.vyle.TanKy.model.Role;
import com.vyle.TanKy.model.auth.LoginRequest;
import com.vyle.TanKy.model.auth.MessageResponse;
import com.vyle.TanKy.model.auth.SignupRequest;
import com.vyle.TanKy.model.auth.UserInfoResponse;
import com.vyle.TanKy.repository.RoleRepository;
import com.vyle.TanKy.repository.UserRepository;
import com.vyle.TanKy.service.auth.UserDetailsImpl;
import com.vyle.TanKy.util.JwtUtils;

@CrossOrigin(origins = "${app.origin}")
@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticatedUser(@Valid @RequestBody LoginRequest loginRequest){
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		System.out.println("userDetails" + userDetails.toString());
		
		ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
		System.out.println("jwt cookie" + jwtCookie.toString());
		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(each -> each.getAuthority())
				.collect(Collectors.toList());
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
				.body(new UserInfoResponse(userDetails.getId(),userDetails.getUsername(), userDetails.getEmail(),roles));
	}
	
	@PostMapping("/internal/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupReq){
		if(userRepository.existsByUsername(signupReq.getUsername()))
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken"));
		if(userRepository.existsByEmail(signupReq.getEmail()))
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken"));
		
		AppUser user = new AppUser(signupReq.getUsername(),signupReq.getEmail(),encoder.encode(signupReq.getPassword()));
		Set<String> strRoles = signupReq.getRole();
		Set<Role> roles = new HashSet<>();
		
		if(strRoles == null){
			Role defaultRole = roleRepository.findByName(EnumRole.ROLE_ADMIN)
					.orElseThrow(()-> new RuntimeException("Error: default role is not found"));
			roles.add(defaultRole);
		} else {
			strRoles.forEach(role ->{
				switch (role) {
					case "superadmin":
						Role superAdminRole = roleRepository.findByName(EnumRole.ROLE_SUPERADMIN)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found"));
						roles.add(superAdminRole);
						break;
					default:
						Role adminRole = roleRepository.findByName(EnumRole.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found"));
						roles.add(adminRole);
				}
			});
		}
		user.setRoles(roles);
		userRepository.save(user);
		return ResponseEntity.ok(new MessageResponse("Successfully register!"));
	}
	
	@PostMapping("/signout")
	public ResponseEntity<?> logoutUser(){
		ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body(new MessageResponse("You have been sign out"));
	}
}
