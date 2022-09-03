package com.shoppingapp.authenticated.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shoppingapp.authenticated.exception.AuthException;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (username.length() != 10 && !isNumeric(username) && username.equals("test")) {
			return new User("test", "test", new ArrayList<>());
		} else if (username.equals("1234567890")) {
			return new User("1234567890", "test", new ArrayList<>());
		} else {
			throw new AuthException("User Not Found");
		}
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
