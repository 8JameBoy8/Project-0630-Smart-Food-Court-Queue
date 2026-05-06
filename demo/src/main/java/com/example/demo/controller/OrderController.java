package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.OrderService;


@RestController
@CrossOrigin
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    //สร้าง order
    @PostMapping("/api/orders")
    public Map<String, Object> create(@RequestBody Map<String, Integer> data) {
        return orderService.createOrder(
            data.get("userId"),
            data.get("storeId"));
    }   

    //เพิ่ม item ใน order
    @PostMapping("/api/orders/{id}/items")
    public Map<String, Object> addItem(@RequestBody Map<String, Object> data, @PathVariable int id) {
    
    int productId = (int) data.get("productId");
    int quantity = (int) data.get("quantity");

    // toppingIds เป็น optional — ถ้าไม่ส่งมาก็ได้
    List<Integer> toppingIds = (List<Integer>) data.getOrDefault("toppingIds", new ArrayList<>());

    return orderService.addItem(id, productId, quantity, toppingIds);
    }

    @PostMapping("/api/orders/{id}/note")
    public Map<String, Object> setNote(@PathVariable int id,@RequestBody Map<String, String> data) {
    return orderService.setNote(id, data.get("note"));
    }

    //นำ order ไปต่อคิว
    @PostMapping("/api/orders/{id}/place")
    public Map<String, Object> place(@PathVariable int id) {
        return orderService.placeOrder(id);
    }

    //ยกเลิก order 
    @PostMapping("/api/orders/{id}/cancel")
    public Map<String, Object> cancel(@PathVariable int id) {
        return orderService.cancelOrder(id);
    }

    //upload รูป
    @PostMapping("/api/payments/{id}/upload")
    public String upload(@RequestBody Map<String, String> data, @PathVariable int id) {
        return orderService.uploadSlip(
            Integer.parseInt(data.get("paymentId")),
            data.get("path")
        );
    }

    // GET order ปัจจุบันของ user
    @GetMapping("/api/users/{id}/order")
    public Map<String, Object> getCurrentOrder(@PathVariable int id) {
        return orderService.getActiveOrder(id);
    }

    // GET ประวัติ order ทั้งหมดของ user
    @GetMapping("/api/users/{id}/orders")
    public List<Map<String, Object>> getOrderHistory(@PathVariable int id) {
        return orderService.getOrderHistory(id);
    }
}