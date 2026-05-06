package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Customer;
import com.example.demo.model.Order;
import com.example.demo.model.Order.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    // ดึง order ของร้าน เรียงตาม ID
    List<Order> findByStore_StoreIDOrderByOrderIDAsc(int storeId);

    List<Order> findByStore_StoreIDAndStatusNotOrderByOrderIDAsc(
    int storeId,
    OrderStatus status
    );

    boolean existsByCustomerAndStatusNot(Customer customer, OrderStatus status);

    Order findByOrderID(int orderID);

    Order findByCustomerUserIDAndStatusNot(int userId, OrderStatus status);
    List<Order> findByCustomerUserID(int userId);

    
}