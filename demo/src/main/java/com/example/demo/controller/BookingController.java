package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class BookingController {
    
    @PostMapping("/api/book")
    public String book(@RequestBody Map<String, Object> data) {
        return "Booked!";
    }

    @GetMapping("/api/currentStatus")
    public Map<String, Object> status() {
        Map<String, Object> res = new HashMap<>();
        res.put("hasBooking", true);
        res.put("tableNum", 5);
        res.put("timeLeft", 20);
        return res;
    }

    @PostMapping("/api/cancel")
    public String cancel() {
        return "Cancelled";
    }

    @GetMapping("/api/bookingHistory")
    public List<Map<String, Object>> history() {
        return new ArrayList<>();
    }
}
