package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.Customer;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String testInsert() {

        Customer user = new Customer("krit","test@gmail.com","1234");

        userRepository.save(user);

        return "inserted!";
    }
}
