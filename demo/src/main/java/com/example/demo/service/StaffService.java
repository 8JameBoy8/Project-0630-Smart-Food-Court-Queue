package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.model.Staff;
import com.example.demo.model.Store;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.StaffRepository;

@Service
public class StaffService {

    private final StaffRepository staffRepository;
    private final OrderRepository orderRepository;
    private final QueueService queueService;

    public StaffService(
        StaffRepository staffRepository,
        OrderRepository orderRepository,
        QueueService queueService
    ) {
        this.staffRepository = staffRepository;
        this.orderRepository = orderRepository;
        this.queueService  = queueService;
    }

    // GET ร้านของ staff คนนี้
    public Map<String, Object> getMyStore(int staffId) {

        Staff staff = staffRepository.findByUserID(staffId);

        if (staff == null) return Map.of("error", "ไม่พบ staff");

        Store store = staff.getStore();

        if (store == null) return Map.of("error", "staff คนนี้ยังไม่มีร้าน");

        // เอา product มาด้วย
        List<Map<String, Object>> products = new ArrayList<>();
        for (Product p : store.getProductList()) {
            Map<String, Object> productData = new HashMap<>();
            productData.put("productId", p.getProductID());
            productData.put("name", p.getName());
            productData.put("price", p.getPrice());
            productData.put("isAvailable", p.isAvailable());
            products.add(productData);
        }

        return Map.of(
            "storeId", store.getStoreID(),
            "storeName", store.getStoreName(),
            "isOpen", store.getIsOpen(),
            "products", products
        );
    }

    // GET order queue ของร้าน staff คนนี้
   public List<Map<String, Object>> getMyOrders(int staffId) {

    Staff staff = staffRepository.findByUserID(staffId);

    if (staff == null || staff.getStore() == null) return List.of();

    // เรียกใช้ QueueService แทนเขียนใหม่
    List<Map<String, Object>> allOrders = queueService
        .getOrdersByStore(staff.getStore().getStoreID());

    // กรองเฉพาะ order ที่ยังไม่เสร็จ
    return allOrders.stream()
        .filter(order -> {
            String status = (String) order.get("status");
            return !status.equals("Cancelled") && !status.equals("Ready");
        })
        .toList();
}
}
