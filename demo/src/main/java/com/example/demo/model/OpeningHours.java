package com.example.demo.model;
import java.time.LocalTime;

public class OpeningHours {
    private LocalTime openTime;
    private LocalTime closeTime;

    //contructor ที่ใส่ข้อมูล เวลาเปิด เวลาปิด เพื่อสร้าง Class OpeningHours และใช้ใน Class Canteen และ Class Store
    public OpeningHours(LocalTime openTime, LocalTime closeTime) {
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    //method เพื่อเช็คว่าเวลาตอนนี้ คือเวลาเปิดหรือเปล่า
    public boolean isOpenNow() {
        LocalTime now = LocalTime.now();
        return !now.isBefore(openTime) && !now.isAfter(closeTime);
    }

    //method get ต่างๆที่เอาขอค่าของ class OpenningHours
    public LocalTime getOpenTime() { return openTime; } //ขอเวลาเปิด
    public LocalTime getCloseTime() { return closeTime; } //ขอเวลาปิด
}