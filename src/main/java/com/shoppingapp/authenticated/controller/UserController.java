package com.shoppingapp.authenticated.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingapp.authenticated.model.UserModel;
import com.shoppingapp.authenticated.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public List<UserModel> getAllUsers() {
		return userService.getUsers();
	}

	@GetMapping("/users/{id}")
	public EntityModel<UserModel> getOneUser(@PathVariable int id) {
		return userService.getUser(id);

	}

	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody UserModel user) {
		return userService.createUser(user);
	}

	@PostMapping("/users/{id}")
	public ResponseEntity<Object> updateUser(@PathVariable int id, @Valid @RequestBody UserModel user) {
		return userService.updateUser(id, user);
	}

}
