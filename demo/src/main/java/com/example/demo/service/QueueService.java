package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.Payment;
import com.example.demo.model.Payment.PaymentStatus;
import com.example.demo.model.Topping;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.StoreRepository;

@Service
public class QueueService {
     private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final StoreRepository storeRepository;
    private final PaymentRepository paymentRepository;
    
    public QueueService(
        OrderRepository orderRepository, 
        CustomerRepository customerRepository, 
        StoreRepository storeRepository, 
        PaymentRepository paymentRepository
    ) 
    {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.storeRepository = storeRepository;
        this.paymentRepository = paymentRepository;
    }  

     //staff verify
    public String verifyPayment(int paymentId, boolean isValid) {

        Payment p = paymentRepository.findByPaymentID(paymentId);

        if (p.getStatus() != PaymentStatus.UPLOADED) {
            return "สถานะไม่ถูกต้อง";
        }

        if (isValid) {
            p.verify();
            paymentRepository.save(p);
            return "ยืนยันสำเร็จ";
        } else {
            p.reject();
            paymentRepository.save(p);
            return "ยืนยันไม่สำเร็จ";
        }
    }

    //ขอข้อมูล order ทั้งหมดที่อยู่ใน 1 ร้าน
    public List<Map<String, Object>> getOrdersByStore(int storeId) {
    
    List<Map<String, Object>> result = new ArrayList<>();

    for (Order order : getQueueByStore(storeId)) {

        Map<String, Object> orderData = new HashMap<>();

            //basic info
            orderData.put("orderId", order.getOrderID());
            orderData.put("status", order.getOrderStatus().toString());
            orderData.put("totalPrice", order.calculateTotalPrice());

            //items
            List<Map<String, Object>> items = new ArrayList<>();

            for (OrderItem item : order.getOrderItemList()) {

                Map<String, Object> itemData = new HashMap<>();
                itemData.put("name", item.getProduct().getName());
                itemData.put("quantity", item.getQuantity());
                itemData.put("price", item.getTotalPrice());

                items.add(itemData);
            }

            orderData.put("items", items);

            //payment
            if (order.getPayment() != null) {
                orderData.put("paymentStatus",
                    order.getPayment().getStatus().toString());
            } else {
                orderData.put("paymentStatus", "NO_PAYMENT");
            }

            result.add(orderData);
        }

        return result;
    }

    //ขอดูข้อมูล order จาก id ที่ให้มา
    public Map<String, Object> getOrder(int orderId) {

        Order order = orderRepository.findByOrderID(orderId);

        if (order == null) return Map.of("error", "ไม่พบ order");

        // items พร้อม toppings
        List<Map<String, Object>> items = new ArrayList<>();
        for (OrderItem item : order.getOrderItemList()) {

            Map<String, Object> itemData = new HashMap<>();
            itemData.put("name", item.getProduct().getName());
            itemData.put("quantity", item.getQuantity());
            itemData.put("price", item.getTotalPrice());

            List<String> toppingNames = item.getSelectedToppings().stream()
                .map(Topping::getToppingName)
                .toList();
            itemData.put("toppings", toppingNames);

            items.add(itemData);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("orderId", order.getOrderID());
        result.put("storeName", order.getStore().getStoreName());
        result.put("status", order.getOrderStatus().toString());
        result.put("totalPrice", order.calculateTotalPrice());
        result.put("itemCount", order.getOrderItemListNum());
        result.put("note", order.getNote() != null ? order.getNote() : "");
        result.put("items", items);

        if (order.getPayment() != null) {
            result.put("paymentStatus", order.getPayment().getStatus().toString());
        } else {
            result.put("paymentStatus", "NO_PAYMENT");
        }

        return result;
    }

    //ขอดูข้อมูล payment จาก id ที่ให้มา
   public Map<String, Object> getPayment(int paymentId) {

    Payment p = paymentRepository.findByPaymentID(paymentId);

    if (p == null) {
        return Map.of("error", "ไม่พบ payment");
    }

    return Map.of(
        "paymentId", paymentId,
        "status", p.getStatus().toString(),
        "price", p.getOrder().getTotalPrice()
    );
}

    public List<Order> getQueueByStore(int storeId) {
        return orderRepository.findByStore_StoreIDOrderByOrderIDAsc(storeId);
    }

    
}
