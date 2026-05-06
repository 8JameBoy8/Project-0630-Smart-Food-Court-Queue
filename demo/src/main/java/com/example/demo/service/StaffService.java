package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.Product;
import com.example.demo.model.Staff;
import com.example.demo.model.Store;
import com.example.demo.model.Topping;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.StaffRepository;

@Service
public class StaffService {

    private final StaffRepository staffRepository;
    private final OrderRepository orderRepository;

    public StaffService(
        StaffRepository staffRepository,
        OrderRepository orderRepository
    ) {
        this.staffRepository = staffRepository;
        this.orderRepository = orderRepository;
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

        int storeId = staff.getStore().getStoreID();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Order order : orderRepository.findByStore_StoreIDOrderByOrderIDAsc(storeId)) {

            // แสดงเฉพาะ order ที่ยังไม่เสร็จ
            if (order.getOrderStatus() == Order.OrderStatus.Cancelled ||
                order.getOrderStatus() == Order.OrderStatus.Ready) {
                continue;
            }

            Map<String, Object> orderData = new HashMap<>();
            orderData.put("orderId", order.getOrderID());
            orderData.put("status", order.getOrderStatus().toString());
            orderData.put("totalPrice", order.calculateTotalPrice());

            if (order.getNote() != null && !order.getNote().isEmpty()) {
                orderData.put("note", order.getNote());
            }

            // items
            List<Map<String, Object>> items = new ArrayList<>();
            for (OrderItem item : order.getOrderItemList()) {
                Map<String, Object> itemData = new HashMap<>();
                itemData.put("name", item.getProduct().getName());
                itemData.put("quantity", item.getQuantity());

                List<String> toppingNames = item.getSelectedToppings().stream()
                    .map(Topping::getToppingName)
                    .toList();
                itemData.put("toppings", toppingNames);
                items.add(itemData);
            }
            orderData.put("items", items);

            // payment status
            if (order.getPayment() != null) {
                orderData.put("paymentStatus", order.getPayment().getStatus().toString());
            } else {
                orderData.put("paymentStatus", "NO_PAYMENT");
            }

            result.add(orderData);
        }

        return result;
    }
}
