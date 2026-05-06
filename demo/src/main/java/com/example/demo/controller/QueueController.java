package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.QueueService;


@RestController
@CrossOrigin
public class QueueController {

    private final QueueService queueService;

    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    //ยืนยันรูป payment
    @PostMapping("/api/payments/{id}/verify")
    public String verify(@RequestBody Map<String, Object> data, @PathVariable int id) {
        return queueService.verifyPayment(
            id,
            (boolean) data.get("isValid")
        );
    }

    @GetMapping("/api/store/{storeId}/orders")
    public List<Map<String, Object>> getStoreQueueOrders(@PathVariable int storeId) {
        return queueService.getOrdersByStore(storeId);
    }

    //ขอข้อมูล order ของ id ที่ให้มา 
    @GetMapping("/api/order/{id}")
    public Map<String, Object> getOrder(@PathVariable int id) {
        return queueService.getOrder(id);
    }
    
    //ขอข้อมูล payment ของ id ที่ให้มา
    @GetMapping("/api/payment/{id}")
    public Map<String, Object> getPayment(@PathVariable int id) {
        return queueService.getPayment(id);
    }

}