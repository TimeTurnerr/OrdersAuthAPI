package com.shoppingapp.authenticated.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingapp.authenticated.model.OrderModel;
import com.shoppingapp.authenticated.service.OrderService;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping("/orders")
	public List<OrderModel> getAllOrders() {
		return orderService.getAllOrders();
	}

	@GetMapping("/orders/{id}")
	public EntityModel<OrderModel> getOneUser(@PathVariable int id) {
		return orderService.getOrder(id);
	}

	@GetMapping("/users/{userId}/orders")
	public List<OrderModel> getAllOrders(@PathVariable int userId) {
		return orderService.getOrders(userId);
	}

	@GetMapping("/users/{userId}/orders/{orderId}")
	public OrderModel getOneOrder(@PathVariable int userId, @PathVariable int orderId) {
		return orderService.getOrder(userId, orderId);
	}

	@PostMapping("/users/{userId}/orders")
	public ResponseEntity<Object> createOrderForUser(@PathVariable int userId, @RequestBody OrderModel newOrder) {
		return orderService.createOrder(userId, newOrder);
	}

	@PostMapping("/users/{userId}/orders/{orderId}")
	public ResponseEntity<Object> updateOrderForUser(@PathVariable int userId, @PathVariable int orderId,
			@RequestBody OrderModel newOrder) {
		return orderService.updateOrder(userId, orderId, newOrder);
	}

}