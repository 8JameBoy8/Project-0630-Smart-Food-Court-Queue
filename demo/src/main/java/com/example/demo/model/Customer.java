package com.example.demo.model;

import java.util.List;
import java.util.Map;

import jakarta.persistence.Entity ;
import jakarta.persistence.Table;


@Entity
@Table(name = "customers")
public class Customer extends User {

    public Customer() {};

    public Customer(String userName, String email, String password) {
        super(userName, email, password); 
    }

    //contructor ที่ใส่ข้อมูล contructor ทั้งหมดของ Super Class user เพื่อสร้าง sub Class Customer
    public Customer(int userID, String userName, String email, String password) {
        super(userID, userName, email, password); 
    }
    
    @Override
    public String getRole() {
        return "customer";
    }

    @Override
    public List<String> getPermissions() {
        return List.of("view_menu", "create_order", "book_table", "upload_slip");
    }

    @Override
    public Map<String, Object> getProfileInfo() {
        return Map.of(
            "userId", userID,
            "name", userName,
            "email", email,
            "role", getRole()
        );
    }
}