package com.example.demo.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Canteen {
    private int canteenID;
    private String canteenName;
    private  List<Table> tables; //ลิสเก็บ class table ของโรงอาหาร
    private  List<Store> stores; //ลิสเก็บ class Store ขอ'โรงอาหาร
    private Map<DayOfWeek, OpeningHours> openingHoursMap = new HashMap<>(); //ใช้ map เพื่อเด็บ key เป็นชื่อวัน และ value เป็น class OpeningHours ที่เป็นเวลาเปิด-ปิดของวันนั้น

    //contructor ที่ใส่ข้อมูล IDโรงอาหาร ชื่อโรงอาหาร เพื่อสร้าง Class Canteen และสร้างลิสของเพื่อเก็บ class table และ store ที่อยู่ในโรงอาหารนั้น
    public Canteen(int canteenID, String canteenName) {
        this.canteenID = canteenID;
        this.canteenName = canteenName;
        this.tables = new ArrayList<>();
        this.stores = new ArrayList<>();
    }

    //เพิ่ม class table เข้าลิส
    public void addTable(Table table) {
        tables.add(table);
    }

    //เพิ่ม class Store เข้าลิส
    public void addStore(Store store) {
        stores.add(store);
    }

    //method เพื่มเวลาปิดเปิดในวันนั้น
    public void setOpeningHours(DayOfWeek day, OpeningHours hours) {
        openingHoursMap.put(day, hours);
    }

    //เช็คว่าเถึงเวลาเปิดรึยัง
    public boolean isOpen() {
         DayOfWeek today = LocalDate.now().getDayOfWeek(); //รับชื่อวันนี้

         OpeningHours hours = openingHoursMap.get(today); //เอาวันที่รับมาที่เป็น key ไปใช้ขอ value เวลาปิดเปิดของวันนั้น

         if (hours == null) {
            return false; // ไม่มีข้อมูล = ปิด
         }

         return hours.isOpenNow(); //เรียก method เพื่อเช็ค
    }

    //ตั้งค่าเวลาเปิดวันปกติทีเดียว
    public void setWeekdayHours(OpeningHours hours) {
    for (DayOfWeek day : DayOfWeek.values()) {
        if (day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY) {
            openingHoursMap.put(day, hours);
             }
        }
    }

    //method get ต่างๆที่เอาขอค่าของ class Canteen
    public int getCanteenID() {return  canteenID; } //ขอ ID ของโรงอาหารนี้
    public String getCanteenName() {return  canteenName;} //ขอชื่อโรงอาหารนี้
    public List<Table> getTableList() {return tables;} //ของลิส CLass Table ของโรงอาหารนี้
    public List<Store> getStoreList() {return stores;} //ขอลิส Class Store ของโรงอาหารนี้
    public int getTableNum() {return tables.size();} //ขอจำนวนโต๊ะในโรงอาหารนี้
    public int getStoreNum() {return stores.size();} //ขอจำนวนร้านอาหารในโรงอาหารนี้
 }