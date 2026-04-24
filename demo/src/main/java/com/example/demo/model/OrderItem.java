package com.example.demo.model;

public class OrderItem {
    private Product product;
    private int quantity;
    private int totalPrice; 

    //contructor ที่ใส่ข้อมูล product จำนวนของProduct เพื่อสร้าง Class OrderItem และคำนวนราคารวมเริ่มต้น
    public OrderItem(Product product, int quantity) {
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