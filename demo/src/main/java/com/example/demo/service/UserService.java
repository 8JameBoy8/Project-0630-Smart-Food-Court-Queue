package com.example.demo.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.model.Customer;
import com.example.demo.model.Staff;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // REGISTER
    public Map<String, Object> register(String name, String email, String password, String role) {

        if (userRepository.findByEmail(email) != null) {
            return Map.of("success", false, "message", "Email ถูกใช้แล้ว");
        }

        User user;

        if ("staff".equalsIgnoreCase(role)) {
            user = new Staff(name, email, password);
        } else {
            user = new Customer(name, email, password);
        }

        userRepository.save(user);

        return Map.of(
            "success", true,
            "userId", user.getuserID(),
            "role", role
        );
    }

    // LOGIN
    public Map<String, Object> login(String email, String password) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            return Map.of("success", false, "message", "ไม่พบ user");
        }

        if (!user.getPassword().equals(password)) {
            return Map.of("success", false, "message", "รหัสผ่านไม่ถูกต้อง");
        }

        String role;

        if (user instanceof Staff) {
            role = "staff";
        } else if (user instanceof Customer) {
            role = "customer";
        } else {
            role = "unknown";
        }

        return Map.of(
            "success", true,
            "userId", user.getuserID(),
            "name", user.getName(),
            "role", role
        );
    }
}