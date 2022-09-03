package com.shoppingapp.authenticated.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "orders")
public class OrderModel {

	@Id
	@SequenceGenerator(name = "myaddresssequence", initialValue = 5)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myaddresssequence")
	private int id;

	@Size(min = 2)
	private String itemName;

	@Min(value = 1)
	private int quantity;

	@Min(value = 10)
	private int price;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private UserModel user;

	public OrderModel() {
	}

	public OrderModel(int id, String itemName, int quantity, int price, UserModel user) {
		this.id = id;
		this.itemName = itemName;
		this.quantity = quantity;
		this.price = price;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "OrderModel [id=" + id + ", itemName=" + itemName + ", quantity=" + quantity + ", price=" + price
				+ ", user=" + user + "]";
	}

}
