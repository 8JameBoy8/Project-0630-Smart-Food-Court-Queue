package com.example.demo.model;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservationId;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private table table;
    private LocalDateTime startTime; //เวลาเปิด
    private LocalDateTime endTime; //เวลาปิด
    private boolean isActive;

    public Reservation() {}

    public Reservation(Customer customer, table table, int durationMinutes) {
        this.customer = customer;
        this.table = table;
        this.startTime = LocalDateTime.now();
        this.endTime = startTime.plusMinutes(durationMinutes);
        this.isActive = true;

        table.setAvailable(false); // จองแล้ว
    }

    //contructor ที่ใส่ข้อมูล id ลูกค้าที่จะจอง โต๊ะที่จะจอง ระยะเวลาการจอง เพื่อสร้าง Class Reservation
    public Reservation(int id, Customer customer, table table, int durationMinutes) {
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

    public void setCustomer(Customer customer) {this.customer = customer;}
    public void setTable(table table) {this.table = table;}
    public void setStartTime() {this.startTime = LocalDateTime.now();}
    public void setEndTime(int durationMinutes) {this.endTime = startTime.plusMinutes(durationMinutes);}
    public void setActive(boolean isActive) {this.isActive = isActive;}

    //method get ต่างๆที่เอาขอค่าของ class Reservation
    public int getReservationID() {return  reservationId;} //ขอ ID ของการจองโต๊ะรายการนี้
    public Customer getCustomer() {return  customer;} //ขอ Class Customer ของการจองโต๊ะรายการนี้
    public table getTable() {return table;} //ขอ Class Table ที่รายการนี้ต้องการจอง
    public boolean isActive() {return isActive;} //ขอสถานะว่าการจองรายการนี้ยังใช้งานอยู่หรือเปล่า
    public boolean isExpired() {return LocalDateTime.now().isAfter(endTime);} //ขอถามว่ารายการหมดเวลาการจองไปหรือยัง
}