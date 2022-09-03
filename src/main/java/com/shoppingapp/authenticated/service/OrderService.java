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

import com.shoppingapp.authenticated.controller.OrderController;
import com.shoppingapp.authenticated.exception.OrderAlreadyExistsException;
import com.shoppingapp.authenticated.exception.OrderNotFoundException;
import com.shoppingapp.authenticated.exception.UserNotFoundException;
import com.shoppingapp.authenticated.model.OrderModel;
import com.shoppingapp.authenticated.model.UserModel;
import com.shoppingapp.authenticated.repository.OrderRepository;
import com.shoppingapp.authenticated.repository.UserRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private UserRepository userRepository;

	public List<OrderModel> getAllOrders() {
		return orderRepository.findAll();
	}

	public EntityModel<OrderModel> getOrder(int id) {
		Optional<OrderModel> order = orderRepository.findById(id);
		if (!order.isPresent()) {
			throw new OrderNotFoundException("Order Not Found");
		}
		EntityModel<OrderModel> model = EntityModel.of(order.get());
		WebMvcLinkBuilder linkToUsers = linkTo(methodOn(OrderController.class).getAllOrders());
		model.add(linkToUsers.withRel("all-orders"));
		return model;
	}

	public List<OrderModel> getOrders(int userId) {
		Optional<UserModel> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			throw new UserNotFoundException("User not found");
		}
		return user.get().getUserOrders();
	}

	public OrderModel getOrder(int userId, int orderId) {
		Optional<UserModel> userOptional = userRepository.findById(userId);
		if (!userOptional.isPresent()) {
			throw new UserNotFoundException("User not found");
		}
		Optional<OrderModel> ordersOptional = orderRepository.findById(orderId);
		if (!ordersOptional.isPresent()) {
			throw new OrderNotFoundException("Order not found");
		}
		UserModel user = userOptional.get();
		if (!user.getUserOrders().contains(ordersOptional.get())) {
			throw new OrderNotFoundException("Order not found for given user");
		} else {
			return ordersOptional.get();
		}
	}

	public ResponseEntity<Object> createOrder(int userId, OrderModel newOrder) {
		Optional<UserModel> userOptional = userRepository.findById(userId);
		if (!userOptional.isPresent()) {
			throw new UserNotFoundException("User not found");
		}
		UserModel user = userOptional.get();
		if (orderExists(userId, newOrder)) {
			throw new OrderAlreadyExistsException("Order Already Exists");
		}
		newOrder.setUser(user);
		orderRepository.save(newOrder);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newOrder.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}

	public ResponseEntity<Object> updateOrder(int userId, int orderId, OrderModel newOrder) {
		OrderModel oldOrder = getOrder(userId, orderId);
		oldOrder.setPrice(newOrder.getPrice());
		oldOrder.setItemName(newOrder.getItemName());
		oldOrder.setQuantity(newOrder.getQuantity());
		orderRepository.save(oldOrder);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newOrder.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}

	public boolean orderExists(int userId, OrderModel order) {
		for (OrderModel c : getOrders(userId)) {
			if (c.getItemName().equals(order.getItemName())) {
				return true;
			}
		}
		return false;
	}

}
