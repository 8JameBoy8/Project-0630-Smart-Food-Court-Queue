package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import com.example.demo.model.Customer;
import com.example.demo.model.Order;
import com.example.demo.model.Order.OrderStatus;
import com.example.demo.model.OrderItem;
import com.example.demo.model.Payment;
import com.example.demo.model.Payment.PaymentStatus;
import com.example.demo.model.Product;
import com.example.demo.model.Store;

@Service
public class OrderService {
    private Map<Integer, Order> orders = new HashMap<>();
    private Map<Integer, Payment> payments = new HashMap<>();
    private Map<Integer, Customer> customers = new HashMap<>();

    private AtomicInteger orderIdGen = new AtomicInteger(1);
    private AtomicInteger paymentIdGen = new AtomicInteger(1);

    private Map<Integer, Store> stores = new HashMap<>();

    public OrderService() {
        // สร้าง store 2 ร้าน (ตอนนี้ยังเป็น in-memory)
        Store s1 = new Store(1, "Store A", null,null);
        Store s2 = new Store(2, "Store B", null,null);

        // add product
        s1.addProduct(1, "Fried Rice", 50);
        s1.addProduct(2, "Water", 10);

        s2.addProduct(3, "Noodle", 60);

        stores.put(1, s1);
        stores.put(2, s2);
    }

    //สร้าง order
    public Map<String, Object> createOrder(int userId, int storeId) {

        Store store = findStoreById(storeId);

        if (store == null) {
            return Map.of("error", "ไม่พบร้าน");
        }

        Customer customer = customers.get(userId);

        int orderId = orderIdGen.getAndIncrement();
        Order order = new Order(orderId, customer, store);

        orders.put(orderId, order);

        return Map.of("orderId", orderId);
    }

    //เพิ่มสินค้า
    public String addItem(int orderId, int productId, int qty) {
        
        Order order = orders.get(orderId);
        Store store = order.getStore();

        if (order == null) {
            return "ไม่พบ order";
        }

        if (order.getOrderStatus() == OrderStatus.Cancelled) {
            return "order ถูกยกเลิก";
            }

        for (Product p : store.getProductList()) {
            if (p.getProductID() == productId) {
                order.addItem(p, qty);
                return "เพิ่มสินค้าแล้ว";
            }
        }

        return "ไม่พบสินค้า";
    }

      //submit order + สร้าง payment
    public Map<String, Object> placeOrder(int orderId) {

        Order order = orders.get(orderId);
        Store store = order.getStore();

        if (order.getOrderStatus() == OrderStatus.Cancelled) {
            return Map.of("error", "order ถูกยกเลิก");
        }

        store.addOrder(order);

        int paymentId = paymentIdGen.getAndIncrement();
        Payment payment = new Payment(paymentId, order);

        order.setPayment(payment);
        payments.put(paymentId, payment);

        return Map.of(
            "message", "กรุณาชำระเงิน",
            "paymentId", paymentId,
            "totalPrice", order.calculateTotalPrice()
        );
    }

    //upload slip
    public String uploadSlip(int paymentId, String path) {

        Payment p = payments.get(paymentId);

        if (p == null) return "ไม่พบ payment";

        p.uploadSlip(path);
        return "อัปโหลดแล้ว";
    }

    //staff verify
    public String verifyPayment(int paymentId, boolean isValid) {

        Payment p = payments.get(paymentId);

        if (p.getStatus() != PaymentStatus.UPLOADED) {
            return "สถานะไม่ถูกต้อง";
        }

        if (isValid) {
            p.verify();
            return "ยืนยันสำเร็จ";
        } else {
            p.reject();
            return "ยืนยันไม่สำเร็จ";
        }
    }

    //helper method หา Store ด้วย id
    private Store findStoreById(int storeId) {
        return stores.get(storeId);
    }

    //ขอข้อมูล order ทั้งหมดที่อยู่ใน 1 ร้าน
    public List<Map<String, Object>> getOrdersByStore(int storeId) {

    Store store = findStoreById(storeId);
    List<Map<String, Object>> result = new ArrayList<>();

    for (Order order : store.getOrderQueue()) {

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

    Order order = orders.get(orderId);

    if (order == null) {
        return Map.of("error", "ไม่พบ order");
    }

    return Map.of(
        "orderId", order.getOrderID(),
        "status", order.getOrderStatus().toString(),
        "totalPrice", order.calculateTotalPrice(),
        "itemCount", order.getOrderItemListNum()
    );
}

    //ขอดูข้อมูล payment จาก id ที่ให้มา
   public Map<String, Object> getPayment(int paymentId) {

    Payment p = payments.get(paymentId);

    if (p == null) {
        return Map.of("error", "ไม่พบ payment");
    }

    return Map.of(
        "paymentId", paymentId,
        "status", p.getStatus().toString()
    );
}

}