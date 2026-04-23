package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/api/userProfile")
    public Map<String, Object> getUserProfile() {
        Map<String, Object> user = new HashMap<>();
        user.put("name", "Jame Boy");
        user.put("studentId", "B63xxxxx");
        user.put("faculty", "Engineering");
        return user;
    }
}