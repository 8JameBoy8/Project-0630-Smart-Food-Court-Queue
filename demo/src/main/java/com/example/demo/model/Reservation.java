package com.example.demo.model;
import java.time.LocalDateTime;

public class Reservation {
    private int reservationId;
    private Customer customer;
    private Table table;
    private LocalDateTime startTime; //เวลาเปิด
    private LocalDateTime endTime; //เวลาปิด
    private boolean isActive;

    //contructor ที่ใส่ข้อมูล id ลูกค้าที่จะจอง โต๊ะที่จะจอง ระยะเวลาการจอง เพื่อสร้าง Class Reservation
    public Reservation(int id, Customer customer, Table table, int durationMinutes) {
        this.reservationId = id;
        this.customer = customer;
        this.table = table;
        this.startTime = LocalDateTime.now();
        this.endTime = startTime.plusMinutes(durationMinutes);
        this.isActive = true;

        table.setAvailable(false); // จองแล้ว
    }

    //method cancel การจองและเปลี่ยนสถานะของโต๊ะที่จองอยู่เป็นว่าง
    public void cancel() {
        this.isActive = false;
        table.setAvailable(true);
    }

    //method get ต่างๆที่เอาขอค่าของ class Reservation
    public int getReservationID() {return  reservationId;} //ขอ ID ของการจองโต๊ะรายการนี้
    public Customer getCustomer() {return  customer;} //ขอ Class Customer ของการจองโต๊ะรายการนี้
    public Table getTable() {return table;} //ขอ Class Table ที่รายการนี้ต้องการจอง
    public boolean isActive() {return isActive;} //ขอสถานะว่าการจองรายการนี้ยังใช้งานอยู่หรือเปล่า
    public boolean isExpired() {return LocalDateTime.now().isAfter(endTime);} //ขอถามว่ารายการหมดเวลาการจองไปหรือยัง
}