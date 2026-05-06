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

import com.example.demo.service.ProductService;

@RestController
@CrossOrigin
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // POST เพิ่ม topping ให้ product
    @PostMapping("/{id}/toppings")
    public Map<String, Object> addTopping(
        @PathVariable int id,
        @RequestBody Map<String, Object> data
    ) {
        return productService.addTopping(
            id,
            (String) data.get("toppingName"),
            (int) data.get("extraPrice")
        );
    }

    // POST เปิด/ปิด topping
    @PostMapping("/toppings/{id}/status")
    public Map<String, Object> setToppingStatus(
        @PathVariable int id,
        @RequestBody Map<String, Boolean> data
    ) {
        return productService.setToppingStatus(id, data.get("isAvailable"));
    }

    // POST เปิด/ปิด product
    @PostMapping("/{id}/status")
    public Map<String, Object> setProductStatus(
        @PathVariable int id,
        @RequestBody Map<String, Boolean> data
    ) {
        return productService.setProductStatus(id, data.get("isAvailable"));
    }

    // GET toppings ของ product
    @GetMapping("/{id}/toppings")
    public List<Map<String, Object>> getToppings(@PathVariable int id) {
        return productService.getToppings(id);
    }
}
