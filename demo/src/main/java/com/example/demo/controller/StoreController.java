package com.example.demo.controller;

import java.util.Map;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.StoreService;

@RestController
@CrossOrigin
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    // POST สร้างร้าน
    @PostMapping
    public Map<String, Object> createStore(@RequestBody Map<String, Object> data) {
        return storeService.createStore(
            (String) data.get("storeName"),
            (int) data.get("staffId"),
            (int) data.get("canteenId")
        );
    }

    // GET เมนูในร้าน
    @GetMapping("/{id}/products")
    public List<Map<String, Object>> getProducts(@PathVariable int id) {
        return storeService.getProducts(id);
    }

    // POST เพิ่มเมนู
    @PostMapping("/{id}/products")
    public Map<String, Object> addProduct(@PathVariable int id, @RequestBody Map<String, Object> data) {
        return storeService.addProduct(
            (String) data.get("name"),
            id,
            (int) data.get("price")
        );
    }

    // POST เปิด/ปิดร้าน
    @PostMapping("/{id}/status")
    public Map<String, Object> setStatus(@PathVariable int id, @RequestBody Map<String, Boolean> data) {
        return storeService.setIsOpen(id, data.get("isOpen"));
    }

    // POST เริ่มทำอาหาร
    @PostMapping("/orders/{id}/cooking")
    public Map<String, Object> startCooking(@PathVariable int id) {
        return storeService.startCooking(id);
    }

    // POST อาหารพร้อม
    @PostMapping("/orders/{id}/ready")
    public Map<String, Object> finishCooking(@PathVariable int id) {
        return storeService.finishCooking(id);
    }
}
