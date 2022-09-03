package com.shoppingapp.authenticated.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.shoppingapp.authenticated.controller.UserController;
import com.shoppingapp.authenticated.exception.UserAlreadyExistsException;
import com.shoppingapp.authenticated.exception.UserNotFoundException;
import com.shoppingapp.authenticated.model.UserModel;
import com.shoppingapp.authenticated.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<UserModel> getUsers() {
		return userRepository.findAll();
	}

	public EntityModel<UserModel> getUser(int id) {
		Optional<UserModel> user = userRepository.findById(id);
		if (!user.isPresent()) {
			throw new UserNotFoundException("User Not Found");
		}
		EntityModel<UserModel> model = EntityModel.of(user.get());
		WebMvcLinkBuilder linkToUsers = linkTo(methodOn(UserController.class).getAllUsers());
		model.add(linkToUsers.withRel("all-users"));
		return model;

	}

	public void deleteUser(int id) {
		userRepository.deleteById(id);
	}

	public ResponseEntity<Object> createUser(UserModel user) {
		if (userExists(user)) {
			throw new UserAlreadyExistsException("User Already Exists");
		}
		UserModel savedUser = userRepository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}

	public ResponseEntity<Object> updateUser(int id, UserModel newUser) {
		Optional<UserModel> user = userRepository.findById(id);
		if (!user.isPresent()) {
			throw new UserNotFoundException("User Not Found");
		}
		UserModel oldUser = user.get();
		oldUser.setUserType(newUser.getUserType());
		oldUser.setName(newUser.getName());
		UserModel savedUser = userRepository.save(oldUser);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}

	public void deleteUserById(int id) {
		userRepository.deleteById(id);
	}

	public boolean userExists(UserModel user) {
		for (UserModel us : getUsers()) {
			if (us.getName().equals(user.getName())) {
				return true;
			}
		}
		return false;
	}

}
