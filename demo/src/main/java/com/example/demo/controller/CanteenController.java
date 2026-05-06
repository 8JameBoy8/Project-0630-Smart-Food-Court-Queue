package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.CanteenService;

@RestController
@CrossOrigin
@RequestMapping("/api/canteens")
public class CanteenController {

    private final CanteenService canteenService;

    public CanteenController(CanteenService canteenService) {
        this.canteenService = canteenService;
    }

    // GET โรงอาหารทั้งหมด
    @GetMapping
    public List<Map<String, Object>> getAllCanteens() {
        return canteenService.getAllCanteens();
    }

    // GET ร้านในโรงอาหาร
    @GetMapping("/{id}/stores")
    public List<Map<String, Object>> getStores(@PathVariable int id) {
        return canteenService.getStoresByCanteen(id);
    }

    // POST สร้างโรงอาหาร
    @PostMapping
    public Map<String, Object> createCanteen(@RequestBody Map<String, String> data) {
        return canteenService.createCanteen(data.get("canteenName"));
    }

    // POST ตั้งเวลาเปิดปิด
    @PostMapping("/{id}/hours")
    public Map<String, Object> setHours(@PathVariable int id, @RequestBody Map<String, String> data) {
        return canteenService.setOpeningHours(
            id,
            data.get("dayType"),
            data.get("openTime"),
            data.get("closeTime")
        );
    }

    // POST เพิ่มโต๊ะ
    @PostMapping("/{id}/tables")
    public Map<String, Object> addTable(@PathVariable int id, @RequestBody Map<String, Integer> data) {
        return canteenService.addTable(id, data.get("tableNo"));
    }
}
