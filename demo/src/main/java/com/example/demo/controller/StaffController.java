package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.StaffService;

@RestController
@CrossOrigin
@RequestMapping("/api/staff")
public class StaffController {

    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    // GET ร้านของตัวเอง
    @GetMapping("/{id}/store")
    public Map<String, Object> getMyStore(@PathVariable int id) {
        return staffService.getMyStore(id);
    }

    // GET order queue ของร้านตัวเอง
    @GetMapping("/{id}/orders")
    public List<Map<String, Object>> getMyOrders(@PathVariable int id) {
        return staffService.getMyOrders(id);
    }
}