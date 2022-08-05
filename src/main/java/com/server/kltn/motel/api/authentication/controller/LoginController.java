package com.server.kltn.motel.api.authentication.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.kltn.motel.api.authentication.payload.JwtAuthentication;
import com.server.kltn.motel.api.authentication.payload.UserLoginDataset;
import com.server.kltn.motel.entity.User;
import com.server.kltn.motel.security.jwt.JwtTokenProvider;
import com.server.kltn.motel.service.UserService;

@RestController
@RequestMapping(value = {"/api/auth"})
public class LoginController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody UserLoginDataset userLoginDataset) {
		

		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
				new UsernamePasswordAuthenticationToken(userLoginDataset.getUsernameOrEmail(), userLoginDataset.getPassword());
		
		Authentication authentication=authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.generateToken(authentication);
		
		if (jwt.equals(null)) {
			return ResponseEntity.ok(new JwtAuthentication(jwt));
		}
		
		User user= userService.findUserByUsernameOrEmail(userLoginDataset.getUsernameOrEmail(), userLoginDataset.getUsernameOrEmail());
		
		List<String> roles= user.getRoles().stream().map(item->item.getName()).collect(Collectors.toList());
		
		return ResponseEntity.ok(new JwtAuthentication(jwt,userLoginDataset.getUsernameOrEmail(), roles));
	}
}
