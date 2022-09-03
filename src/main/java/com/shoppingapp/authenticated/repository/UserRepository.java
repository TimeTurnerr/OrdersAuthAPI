package com.shoppingapp.authenticated.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoppingapp.authenticated.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {

}
