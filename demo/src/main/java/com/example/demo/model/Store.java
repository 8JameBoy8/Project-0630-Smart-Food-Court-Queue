package com.example.demo.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "stores")
public class Store {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int storeID;

    private String storeName;
    private boolean isOpen;

    @OneToOne
    @JoinColumn(name ="StaffID")
    private Staff staff;

    @ManyToOne
    @JoinColumn(name ="CanteenID")
    private Canteen canteen;

    @OneToMany(mappedBy = "store")
    private List<Product> productsList; //ลิสเก็บ class product ที่ขายของร้านค้า

    @OneToMany(mappedBy = "store")
    private List<Order> orderQueue; //ลิสเก็บคิว order ที่ถูกสั่งมาให้ร้านนี้

    

    public Store() {
        this.productsList = new ArrayList<>();
        this.orderQueue = new LinkedList<>();}

    public Store(String storeName,Staff staff,Canteen canteen) {
        this.storeName = storeName;
        this.staff = staff;
        this.canteen = canteen;
        this.productsList = new ArrayList<>();
        this.orderQueue = new LinkedList<>();
    }

    //contructor ที่ใส่ข้อมูล id ชื่อ Staff ที่เป็นเจ้าชองร้าน เพื่อสร้าง Class Store และสร้างลิสรายการเพื่อเก็บ Product ที่มีอยู่ใน Store นี้ และ ลิสคิวที่เก็บ Order ที่ถูกสั่งมาให้ร้านนี้
    public Store(int storeID, String storeName,Staff staff,Canteen canteen) {
        this.storeID = storeID;
        this.storeName = storeName;
        this.staff = staff;
        this.canteen = canteen;
        this.productsList = new ArrayList<>();
        this.orderQueue = new LinkedList<>();
    }

    

    public void setStoreName(String storeName) {this.storeName = storeName;}
    public void setStaff(Staff staff) {this.staff = staff;}
    public void setCanteen(Canteen canteen) {this.canteen = canteen;}
    public void setIsOpen(boolean isOpen) {this.isOpen = isOpen;}


    //method get ต่างๆที่เอาขอค่าของ class Store
    public Staff getStaff() {return  staff;} //ขอ Class Staff ที่เป็รเจ้าของ Store นี้
    public int getStoreID() {return  storeID;} //ขอ ID ของ Store
    public String getStoreName() {return  storeName;} //ขอชื่อ Store
    public List<Product> getProductList() { return productsList; } //ของลิสรายการโปรดักในร้านทั้งหมด
    public List<Order> getOrderQueue() { return orderQueue; } //ชอลิสคิว Order ของร้านทั้งหมด
    public boolean getIsOpen() {return isOpen;}
    
}