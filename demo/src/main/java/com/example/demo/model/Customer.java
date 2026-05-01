package com.example.demo.model;

import jakarta.persistence.Entity ;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "customers")
public class Customer extends User {
    
    @OneToOne
    private Reservation currentReservation; //ตัวแปรการจองโต๊ะของลูกค้าคนนี้
    @OneToOne
    private Order currentOrder; //ตัวแปรการสั่งอาหารของลูกค้าคนนี้

    public Customer() {};

    public Customer(String userName, String email, String password) {
        super(userName, email, password); 
    }

    //contructor ที่ใส่ข้อมูล contructor ทั้งหมดของ Super Class user เพื่อสร้าง sub Class Customer
    public Customer(int userID, String userName, String email, String password) {
        super(userID, userName, email, password); 
    }
    
    //method สร้างการจองโต๊ะ
    public Reservation reserveTable(table table, int durationMinutes, int reservationId) {

        // เช็คว่ามีการจองอยู่แล้วไหม
        if (currentReservation != null && currentReservation.isActive()) {
          System.out.println("คุณมีการจองอยู่แล้ว");
            return null;
    }

         if (!table.isAvailable()) {
            System.out.println("โต๊ะไม่ว่าง");
            return null;
        }

        currentReservation = new Reservation(
            reservationId,
            this,
            table,
            durationMinutes
        );

        return currentReservation;
    }
    
    //ยกเลิกการจองโต๊ะ
    public void cancelReservation() {
        if (currentReservation != null) {
          currentReservation.cancel(); //เรียก mthod cancel การจอง
          currentReservation = null; //เปลี่ยนตัวแปรเป็นไม่มีการจอง
        }

    }

    //method เริ่มสั่งอาหาร สร้าง class order
    public void startOrder(int id,Store store) {
        if (currentOrder != null) {
           System.out.println("คุณมีออเดอร์อยู่แล้ว");
           return;
         }

         currentOrder = new Order(id, this, store);
    }

    //method เพิ่มอาหารเข้า order 
    public void addItemToOrder(Product product, int quantity) {
    if (currentOrder == null) {
        System.out.println("ยังไม่ได้เริ่มสั่งอาหาร");
        return;
    }

    currentOrder.addItem(product, quantity); //เรียกใช้ method ของ currentorder ที่เป็น class order
}

    //method เข้าลิสคิว order ใน Store ที่สั่งอาหาร
    public void placeOrder() {
         if (currentOrder == null) {
              System.out.println("ไม่มีออเดอร์");
              return;
            }

        currentOrder.getStore().addOrder(currentOrder); //เรียกใช้ getStore ใน class order เพื่อเรียกใช้ method ของ store นั้นเพื่อเพิ่มคิวเข้าลิส

        currentOrder = null; // เคลียร์หลังส่ง
}

    //method get ต่างๆที่เอาขอค่าของ class Customer
    public Reservation getReservations() {return currentReservation;} //รายการจองโต๊ะของลูกค้าคนนี้
    public Order getCurrentorder() {return  currentOrder;} //ขอรายการสั่งอาหารของลูกค้าคนนี้
}