package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "orderitems")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderItemid;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Order order;

    private int quantity;
    private int totalPrice; 

    //contructor ที่ใส่ข้อมูล product จำนวนของProduct เพื่อสร้าง Class OrderItem และคำนวนราคารวมเริ่มต้น
    public OrderItem(Order order, Product product, int quantity) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = product.getPrice() * quantity;
    }

    //method เพิ่มจำนวนครั้งละ 1
    public void addQuantity() {
        quantity += 1;
        updateTotalPrice(); //อัพเดตราคารวมหลังเปลี่ยนจำนวน
    }

    //method ลดจำนวนครั้งละ 1
    public void reduceQuantity() {
        if ((quantity-1) > 0) {
            quantity -= 1;
            updateTotalPrice();  //อัพเดตราคารวมหลังเปลี่ยนจำนวน
        }
    }

    //method กำหนดจำวนเอง
    public void setQuantity(int quantity) {
        if (quantity > 0) {
            this.quantity = quantity;
            updateTotalPrice(); //อัพเดตราคารวมหลังเปลี่ยนจำนวน
        }
    }

    //method คำนวนราคารวมทั้งหมด
    private void updateTotalPrice() {
        this.totalPrice = product.getPrice() * quantity;
    }

    //method get ต่างๆที่เอาขอค่าของ class OrderItem
    public Product getProduct() { return product; } //ขอ Class Product ของ Product ที่ต้องการจะสั่ง
    public int getQuantity() {return  quantity; } //ขอจำนวนของ Product นี้ที่ต้องการจะสั่ง
    public int getTotalPrice() { return  totalPrice; } //ขอราคารวมของ Product จำนวนเท่านั้น (product.price*quantity)

}