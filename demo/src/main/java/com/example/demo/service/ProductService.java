package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.model.Topping;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ToppingRepository;



@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ToppingRepository toppingRepository;

    public ProductService(
        ProductRepository productRepository,
        ToppingRepository toppingRepository
    ) {
        this.productRepository = productRepository;
        this.toppingRepository = toppingRepository;
    }

    public Map<String, Object> addTopping(int productId, String toppingName, int extraPrice) {

        Product product = productRepository.findByProductID(productId);

        if (product == null) return Map.of("error", "ไม่พบสินค้า");

        Topping topping = new Topping(toppingName, extraPrice);
        toppingRepository.save(topping);

        product.addTopping(topping);
        productRepository.save(product);

        return Map.of(
            "toppingId", topping.getToppingID(),
            "toppingName", topping.getToppingName(),
            "extraPrice", topping.getExtraPrice()
        );
    }

    public Map<String, Object> setToppingStatus(int toppingId, boolean status) {

        Topping topping = toppingRepository.findById(toppingId).orElse(null);

        if (topping == null) return Map.of("error", "ไม่พบ topping");

        topping.setAvailable(status);
        toppingRepository.save(topping);

        return Map.of("toppingId", toppingId, "isAvailable", status);
    }

    public Map<String, Object> setProductStatus(int productId, boolean status) {

        Product product = productRepository.findByProductID(productId);

        if (product == null) return Map.of("error", "ไม่พบสินค้า");

        product.setAvailable(status);
        productRepository.save(product);

        return Map.of("productId", productId, "isAvailable", status);
    }

    public List<Map<String, Object>> getToppings(int productId) {

        Product product = productRepository.findByProductID(productId);

        if (product == null) return List.of();

        List<Map<String, Object>> result = new ArrayList<>();

        for (Topping t : product.getAvailableToppings()) {
            Map<String, Object> data = new HashMap<>();
            data.put("toppingId", t.getToppingID());
            data.put("toppingName", t.getToppingName());
            data.put("extraPrice", t.getExtraPrice());
            data.put("isAvailable", t.isAvailable());
            result.add(data);
        }

        return result;
    }
}
