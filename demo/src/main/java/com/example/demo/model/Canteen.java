package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "canteens")
public class Canteen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int canteenID;
    private String canteenName;

    @OneToMany(mappedBy = "canteen")
    private  List<table> tables; //ลิสเก็บ class table ของโรงอาหาร

    @OneToMany(mappedBy = "canteen")
    private  List<Store> stores; //ลิสเก็บ class Store ขอ'โรงอาหาร

    @OneToMany(mappedBy = "canteen")
    private List<OpeningHours> openingHours = new ArrayList<>(); // เวลาเปิดปิด

    public Canteen() {
        this.tables = new ArrayList<>();
        this.stores = new ArrayList<>();
    }

    public Canteen(String canteenName) {
        this.canteenName = canteenName;
        this.tables = new ArrayList<>();
        this.stores = new ArrayList<>();
    }
    //contructor ที่ใส่ข้อมูล IDโรงอาหาร ชื่อโรงอาหาร เพื่อสร้าง Class Canteen และสร้างลิสของเพื่อเก็บ class table และ store ที่อยู่ในโรงอาหารนั้น
    public Canteen(int canteenID, String canteenName) {
        this.canteenID = canteenID;
        this.canteenName = canteenName;
        this.tables = new ArrayList<>();
        this.stores = new ArrayList<>();
    }

    //เพิ่ม class table เข้าลิส
    public void addTable(table table) {
        tables.add(table);
    }

    //เพิ่ม class Store เข้าลิส
    public void addStore(Store store) {
        stores.add(store);
    }


    public void setCanteenName(String canteenName) { this.canteenName = canteenName;}

    //method get ต่างๆที่เอาขอค่าของ class Canteen
    public int getCanteenID() {return  canteenID; } //ขอ ID ของโรงอาหารนี้
    public String getCanteenName() {return  canteenName;} //ขอชื่อโรงอาหารนี้
    public List<table> getTableList() {return tables;} //ของลิส CLass Table ของโรงอาหารนี้
    public List<Store> getStoreList() {return stores;} //ขอลิส Class Store ของโรงอาหารนี้
    public int getTableNum() {return tables.size();} //ขอจำนวนโต๊ะในโรงอาหารนี้
    public int getStoreNum() {return stores.size();} //ขอจำนวนร้านอาหารในโรงอาหารนี้
    public List<OpeningHours> getOpeningHours() { return openingHours; }
 }