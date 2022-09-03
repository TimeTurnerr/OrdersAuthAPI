package com.shoppingapp.authenticated.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class UserModel {

	@Id
	@SequenceGenerator(name = "myusersequence", initialValue = 4)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myusersequence")
	private int id;

	@Size(min = 2)
	private String name;

	private String userType;

	@OneToMany(mappedBy = "user")
	private List<OrderModel> userOrders;

	public UserModel() {
	}

	public UserModel(Integer id, String name, String userType) {
		this.id = id;
		this.name = name;
		this.userType = userType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public List<OrderModel> getUserOrders() {
		return userOrders;
	}

	public void setUserOrders(List<OrderModel> userOrders) {
		this.userOrders = userOrders;
	}

	@Override
	public String toString() {
		return "UserModel [id=" + id + ", name=" + name + ", userType=" + userType + ", userOrders=" + userOrders + "]";
	}

}
