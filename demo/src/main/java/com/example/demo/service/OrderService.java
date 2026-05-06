package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.model.Customer;
import com.example.demo.model.Order;
import com.example.demo.model.Order.OrderStatus;
import com.example.demo.model.OrderItem;
import com.example.demo.model.Payment;
import com.example.demo.model.Product;
import com.example.demo.model.Store;
import com.example.demo.model.Topping;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.StoreRepository;
import com.example.demo.repository.ToppingRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final StoreRepository storeRepository;
    private final PaymentRepository paymentRepository;
    private final ProductRepository productRepository;
    private final ToppingRepository toppingRepository;


    public OrderService(
        OrderRepository orderRepository, 
        CustomerRepository customerRepository, 
        StoreRepository storeRepository, 
        PaymentRepository paymentRepository,
        ProductRepository productRepository,
        ToppingRepository toppingRepository
    ) 
    {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.storeRepository = storeRepository;
        this.paymentRepository = paymentRepository;
        this.productRepository = productRepository;
        this.toppingRepository = toppingRepository;
    }   

    //สร้าง order
    public Map<String, Object> createOrder(int userId, int storeId) {

        // เช็ค null จาก request
        if (userId <= 0 || storeId <= 0) {
            return Map.of("error", "userId และ storeId จำเป็นต้องมี");
        }

        Store store = storeRepository.findByStoreID(storeId);   

        if (store == null) {
            return Map.of("error", "ไม่พบร้าน");
        }

            // เช็คว่าร้านเปิดอยู่ไหม
        if (!store.getIsOpen()) {
            return Map.of("error", "ร้านปิดอยู่");
        }

        Customer customer = customerRepository.findByUserID(userId);

        boolean hasActiveOrder = orderRepository.existsByCustomerAndStatusNot(customer, OrderStatus.Cancelled);

        if (hasActiveOrder) {
            return Map.of("success", false, "message", "มีคำสั่งซื้อที่ยังดำเนินการอยู่");
        }

        Order order = new Order(customer, store);

        orderRepository.save(order);

        return Map.of("orderId", order.getOrderID());
    }

    //เพิ่มสินค้า
    public Map<String, Object> addItem(int orderId, int productId, int qty, List<Integer> toppingIds) {

    Order order = orderRepository.findByOrderID(orderId);

    if (order == null) {
        return Map.of("error", "ไม่พบ order");
    }

    if (order.getOrderStatus() == OrderStatus.Cancelled) {
        return Map.of("error", "order ถูกยกเลิก");
    }

    Product product = productRepository.findById(productId).orElse(null);

    if (product == null) {
        return Map.of("error", "ไม่พบสินค้า");
    }

    // โหลด topping ที่เลือก (ถ้าไม่ส่งมาหรือส่งมาว่างก็ข้ามไป)
    List<Topping> selectedToppings = new ArrayList<>();

    if (toppingIds != null && !toppingIds.isEmpty()) {
        selectedToppings = toppingRepository.findAllById(toppingIds);
    }

    order.addItem(product, qty, selectedToppings);
    orderRepository.save(order);

    return Map.of("success", "เพิ่มสินค้าแล้ว");
    }

    public Map<String, Object> setNote(int orderId, String note) {

        Order order = orderRepository.findByOrderID(orderId);

        if (order == null) return Map.of("error", "ไม่พบ order");

        if (order.getOrderStatus() != OrderStatus.Pending) {
            return Map.of("error", "ไม่สามารถแก้ note ได้ เพราะ order ไม่ได้อยู่ในสถานะ Pending");
        }

        order.setNote(note);
        orderRepository.save(order);

        return Map.of("orderId", orderId, "note", note);
    }

      //submit order + สร้าง payment
    public Map<String, Object> placeOrder(int orderId) {

        Order order = orderRepository.findByOrderID(orderId);
        Store store = order.getStore();

        if (order.getOrderStatus() == OrderStatus.Cancelled) {
            return Map.of("error", "order ถูกยกเลิก");
        }

        orderRepository.save(order);

        Payment payment = new Payment(order);

        order.setPayment(payment);
        paymentRepository.save(payment);

        return Map.of(
            "message", "กรุณาชำระเงิน",
            "paymentId", payment.getPaymentID(),
            "totalPrice", order.getTotalPrice()
        );
    }

    public Map<String, Object> cancelOrder(int orderId) {

        Order order = orderRepository.findByOrderID(orderId);
        order.cancelOrder();
        orderRepository.save(order);

        return  Map.of("orderId", order.getOrderID());
    }

    //upload slip
    public String uploadSlip(int paymentId, String path) {

        Payment p = paymentRepository.findByPaymentID(paymentId);

        if (p == null) return "ไม่พบ payment";
        
        p.uploadSlip(path);
        paymentRepository.save(p);
        return "อัปโหลดแล้ว";

        
    }

     // GET order ที่ยัง active อยู่
    public Map<String, Object> getActiveOrder(int userId) {

        Customer customer = customerRepository.findByUserID(userId);

        if (customer == null) return Map.of("error", "ไม่พบผู้ใช้");

        Order order = orderRepository.findByCustomerUserIDAndStatusNot(userId, OrderStatus.Cancelled);

        if (order == null) return Map.of("message", "ไม่มี order ที่ active อยู่");

        List<Map<String, Object>> items = new ArrayList<>();

        for (OrderItem item : order.getOrderItemList()) {

            Map<String, Object> itemData = new HashMap<>();
            itemData.put("name", item.getProduct().getName());
            itemData.put("quantity", item.getQuantity());
            itemData.put("price", item.getTotalPrice());

            // topping ที่เลือก
            List<String> toppingNames = item.getSelectedToppings().stream()
                .map(Topping::getToppingName)
                .toList();
            itemData.put("toppings", toppingNames);

            items.add(itemData);
        }

        return Map.of(
            "orderId", order.getOrderID(),
            "storeName", order.getStore().getStoreName(),
            "status", order.getOrderStatus().toString(),
            "totalPrice", order.calculateTotalPrice(),
            "note", order.getNote() != null ? order.getNote() : "",
            "items", items
        );
    }

    // GET ประวัติ order ทั้งหมด
    public List<Map<String, Object>> getOrderHistory(int userId) {

        Customer customer = customerRepository.findByUserID(userId);

        if (customer == null) return List.of();

        List<Map<String, Object>> result = new ArrayList<>();

        for (Order order : orderRepository.findByCustomerUserID(userId)) {

            Map<String, Object> data = new HashMap<>();
            data.put("orderId", order.getOrderID());
            data.put("storeName", order.getStore().getStoreName());
            data.put("status", order.getOrderStatus().toString());
            data.put("totalPrice", order.getTotalPrice());
            result.add(data);
        }

        return result;
    }

    
}