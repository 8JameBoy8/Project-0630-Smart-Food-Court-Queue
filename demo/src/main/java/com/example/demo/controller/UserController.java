package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.UserService;

@RestController
public class UserController {
    
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/userProfile")
    public Map<String, Object> getUserProfile() {
        Map<String, Object> user = new HashMap<>();
        user.put("name", "Jame Boy");
        user.put("studentId", "B63xxxxx");
        user.put("faculty", "Engineering");
        return user;
    }

    @GetMapping("/api/test-db")
    public String testDB() {
        return userService.testInsert();
    }
}