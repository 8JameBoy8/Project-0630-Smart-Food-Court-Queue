package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;

    @OneToOne(mappedBy = "payment")
    private Order order;
    private String slipImagePath; // path รูป
    private PaymentStatus status; //สถานะของการจ่ายเงิน
    
    public enum PaymentStatus {
        PENDING,
        UPLOADED,     
        VERIFIED,     
        REJECTED     
    }

    public Payment() {
        this.status = PaymentStatus.PENDING; 
    }

    public Payment(Order order) {
        this.order = order;
        this.status = PaymentStatus.PENDING; 
    }

    //contructor ที่ใส่ข้อมูล IDpayment ออเดอร์ที่จะจ่าย เพื่อสร้าง Class Payment และเปลี่ยนสถานะเป็นรอดำเนินการ
    public Payment(int paymentId, Order order) {
        this.paymentId = paymentId;
        this.order = order;
        this.status = PaymentStatus.PENDING; 
    }

    // ลูกค้าอัปโหลดสลิป
    public void uploadSlip(String imagePath) {
        this.slipImagePath = imagePath; //รับ path/directory ของรูปที่อัปโหลดเข้ามา (example c/image/pic.jpg)
        this.status = PaymentStatus.UPLOADED; //เปลี่ยนสถานะการจ่ายเงิน
    }

    // ร้านยืนยัน
    public void verify() {
        this.status = PaymentStatus.VERIFIED; //เปลี่ยนสถานะการจ่ายเงิน
        order.updateStatus(Order.OrderStatus.Received); //เปลี่ยนสถานะของ order เป็นรับแล้วหลังจากให้ Staff เช็ครูป
    }

    // ร้านปฏิเสธ
    public void reject() {
        this.status = PaymentStatus.REJECTED; //เปลี่ยนสถานะการจ่ายเงิน

        // ยกเลิก order
        order.cancelOrder();
    }

    public void setOrder(Order order) {this.order = order;}
    
    //method get ต่างๆที่เอาขอค่าของ class Payment
    public Order getOrder() {return order;}
    public int getPaymentID() {return paymentId;}
    public PaymentStatus getStatus() { return status; } //ขอสถานะการจ่ายเงินของรายการนี้
}