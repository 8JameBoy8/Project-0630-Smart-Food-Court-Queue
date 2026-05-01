package com.example.demo.controller;

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

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    //สร้าง order
    @PostMapping("/api/order/create")
    public Map<String, Object> create(@RequestBody Map<String, Integer> data) {
        return service.createOrder(
            data.get("userId"),
            data.get("storeId"));
    }

    //เพิ่ม item ใน order
    @PostMapping("/api/order/addItem")
    public String addItem(@RequestBody Map<String, Integer> data) {
        return service.addItem(
            data.get("orderId"),
            data.get("productId"),
            data.get("quantity")
        );
    }

    //นำ order ไปต่อคิว
    @PostMapping("/api/order/place")
    public Map<String, Object> place(@RequestBody Map<String, Integer> data) {
        return service.placeOrder(data.get("orderId"));
    }

    //upload รูป
    @PostMapping("/api/payment/upload")
    public String upload(@RequestBody Map<String, String> data) {
        return service.uploadSlip(
            Integer.parseInt(data.get("paymentId")),
            data.get("path")
        );
    }

    //ยืนยันรูป payment
    @PostMapping("/api/payment/verify")
    public String verify(@RequestBody Map<String, Object> data) {
        return service.verifyPayment(
            (int) data.get("paymentId"),
            (boolean) data.get("isValid")
        );
    }

    @GetMapping("/api/store/{storeId}/orders")
    public List<Map<String, Object>> getStoreOrders(@PathVariable int storeId) {
        return service.getOrdersByStore(storeId);
    }

    //ขอข้อมูล order ของ id ที่ให้มา 
    @GetMapping("/api/order/{id}")
    public Map<String, Object> getOrder(@PathVariable int id) {
        return service.getOrder(id);
    }
    
    //ขอข้อมูล payment ของ id ที่ให้มา
    @GetMapping("/api/payment/{id}")
    public Map<String, Object> getPayment(@PathVariable int id) {
        return service.getPayment(id);
    }

}