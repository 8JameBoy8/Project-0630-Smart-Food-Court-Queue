package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.model.Canteen;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.model.Staff;
import com.example.demo.model.Store;
import com.example.demo.model.Topping;
import com.example.demo.repository.CanteenRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.StaffRepository;
import com.example.demo.repository.StoreRepository;

@Service
public class StoreService {
     private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final StoreRepository storeRepository;
    private final PaymentRepository paymentRepository;
    private final ProductRepository productRepository;
    private final StaffRepository staffRepository;
     private final CanteenRepository canteenRepository;
    
    public StoreService(
        OrderRepository orderRepository, 
        CustomerRepository customerRepository, 
        StoreRepository storeRepository, 
        PaymentRepository paymentRepository,
        ProductRepository productRepository,
        StaffRepository staffRepository,
        CanteenRepository canteenRepository
    ) 
    {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.storeRepository = storeRepository;
        this.paymentRepository = paymentRepository;
        this.productRepository = productRepository;
        this.staffRepository = staffRepository;
        this.canteenRepository = canteenRepository;
    }
    
    public Map<String, Object> createStore(String storeName, int staffId, int canteenId) {
        Staff staff = staffRepository.findByUserID(staffId);
        Canteen canteen = canteenRepository.findByCanteenID(canteenId);
        Store s = new Store(storeName, staff, canteen);
        storeRepository.save(s);
        return Map.of("storeId", s.getStoreID(), "storeName", s.getStoreName());
    }

    public Map<String, Object> addProduct(String name, int storeId, int price) {
        Store store = storeRepository.findByStoreID(storeId);
        Product p = new Product(name, store, price);
        productRepository.save(p);
        return Map.of("productId", p.getProductID(), "name", p.getName(), "price", p.getPrice());
    }

    public Map<String, Object> setIsOpen(int storeId, boolean status) {
        Store s = storeRepository.findByStoreID(storeId);
        s.setIsOpen(status);
        storeRepository.save(s);
        return Map.of("storeId", storeId, "isOpen", status);
    }

    public Map<String,Object> startCooking(int orderID) {

        Order order = orderRepository.findByOrderID(orderID);
        order.updateStatus(Order.OrderStatus.Cooking);
        orderRepository.save(order); 

        return Map.of("orderID",order.getOrderID(),"orderStatus",order.getOrderStatus());
    }

    public Map<String,Object> finishCooking(int orderID) {

        Order order = orderRepository.findByOrderID(orderID);
        order.updateStatus(Order.OrderStatus.Ready);
        orderRepository.save(order); 

        return Map.of("orderID",order.getOrderID(),"orderStatus",order.getOrderStatus());
    }
    

    // GET เมนูในร้าน
    public List<Map<String, Object>> getProducts(int storeId) {

        Store store = storeRepository.findByStoreID(storeId);

        if (store == null) return List.of();

        List<Map<String, Object>> result = new ArrayList<>();

        for (Product p : store.getProductList()) {

            Map<String, Object> data = new HashMap<>();
            data.put("productId", p.getProductID());
            data.put("name", p.getName());
            data.put("price", p.getPrice());
            data.put("isAvailable", p.isAvailable());

            // topping ที่มีให้เลือก
            List<Map<String, Object>> toppings = new ArrayList<>();
            for (Topping t : p.getAvailableToppings()) {
                if (t.isAvailable()) {
                    Map<String, Object> toppingData = new HashMap<>();
                    toppingData.put("toppingId", t.getToppingID());
                    toppingData.put("name", t.getToppingName());
                    toppingData.put("extraPrice", t.getExtraPrice());
                    toppings.add(toppingData);
                }
            }
            data.put("toppings", toppings);
            result.add(data);
        }

        return result;
    }
}
