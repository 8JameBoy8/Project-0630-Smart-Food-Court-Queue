package com.example.demo.model;

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
    
   
}