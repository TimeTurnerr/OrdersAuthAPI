package com.shoppingapp.authenticated.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingapp.authenticated.exception.AuthException;
import com.shoppingapp.authenticated.model.JwtRequest;
import com.shoppingapp.authenticated.model.JwtResponse;
import com.shoppingapp.authenticated.service.JwtUtil;
import com.shoppingapp.authenticated.service.MyUserDetailsService;

@RestController
public class JwtController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/auth")
	public ResponseEntity<Object> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
		System.out.println(jwtRequest);

		String usernameWithType;
		if (jwtRequest.getUserType().equals("name")) {
			usernameWithType = jwtRequest.getUsername();
		} else if (jwtRequest.getUserType().equals("phone")) {
			if (jwtRequest.getUserType().length() != 10 && !isNumeric(jwtRequest.getUsername())) {
				throw new AuthException("Bad Credentials");
			}
			usernameWithType = jwtRequest.getUsername();
		} else {
			throw new AuthException("Bad Credentials");
		}

		try {

			this.authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(usernameWithType, jwtRequest.getPassword()));
		} catch (AuthException e) {
			throw new AuthException("Bad Credentials");
		}

		UserDetails userDetails = this.myUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
		String token = this.jwtUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}

	public static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			int d = Integer.parseInt(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

}
