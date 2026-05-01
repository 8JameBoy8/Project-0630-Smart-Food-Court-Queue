package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// คลาสแม่ (Superclass) สำหรับสินค้าในร้าน
@Entity
@Table(name = "products")
public  class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected  int productID;
    protected  String productName;

    @ManyToOne
    protected   Store store;
    protected  int price;
    protected   boolean isAvailable=true; //ตัวแปรเช็คสถานะ

    //contructor ที่ใส่ข้อมูล id ชื่อ ร้านอาหารที่ product นี้อยู่ ราคา เพื่อสร้าง Class Product
    public Product(int id, String name,Store store, int price) {
        this.productID = id;
        this.productName = name;
        this.store = store;
        this.price = price;
    }

    //method เปลียนสถานะของ product ว่าขายอยู่หรือไม่พร้อมขาย
    public void setAvailable(boolean status) {
        this.isAvailable = status;
    }

    //method get ต่างๆที่เอาขอค่าของ class Product
    public int getProductID() {return  productID; } //ขอ ID ของ Product
    public String getName() { return productName; } //ขอชื่อของ Product
    public int getPrice() { return price; } //ขอราคาของ Product
    public Store getStore() {return  store; } //ขอ Class Store ที่ Product นี้อยู่
    public boolean isAvailable() {return  isAvailable; } //ขอถามว่า Product ตัวนี้ยังขายอยู่หรือไม่พร้อมขาย
    
    
}
