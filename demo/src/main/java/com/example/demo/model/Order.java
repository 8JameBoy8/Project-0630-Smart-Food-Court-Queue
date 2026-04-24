package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderID;
    private Customer customer;
    private Store store;
    private Payment payment;
    private  List<OrderItem> orderItems;  //ลิสเก็บ orderItem ที่เก็บ product และจำนวน
    private OrderStatus status; //สถานะของ order

    public enum OrderStatus {
        Pending,
        Received,
        Cooking,
        Ready,
        Cancelled
    }

    //contructor ที่ใส่ข้อมูล orderID ลุกค้าที่จะสั่ง order นี้ ร้านที่รับ order นี้ เพื่อสร้าง Class Order และสร้างลิส orderItem มาเพื่อเตรียมรับรายการอาหารและตั้งค่าสถานะออเดอร์
    // เป็นรอดำเนินการ
    public Order(int orderID, Customer customer,Store store){
        this.orderID = orderID;
        this.customer = customer;
        this.store = store;
        this.orderItems = new ArrayList<>();
        this.status = OrderStatus.Pending;
    }

    //method เพื่อรับ class payment 
    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    //method เพื่มอาหารและจำนวนเข้าลิส orderItem
     public void addItem(Product product, int quantity) {
        for (OrderItem item : orderItems) {

            //เช็คว่าเพิ่มอาหารอันเดิมซ้ำรึเปล่า
            if (item.getProduct().equals(product)) {
                item.setQuantity(quantity);
                return;
            }
        }

        orderItems.add(new OrderItem(product, quantity)); //สร้าง class orderItem ที่รับค่าเข้ามาและเพิ่มแข้าไปในลิส orderItem ของ order นี้
    }

    //method ลบอาหารที่อยู่ในลิส
     public void removeItem(Product product) {
        orderItems.removeIf(item -> item.getProduct().equals(product)); //รับค่า product ที่จะลบแล้วเช็คว่ามี product ไหรในลิสคือ product นั้นและลบออก
    }

    //method คำนวนเงินของทุก orderItem ในลิส
    public double calculateTotalPrice() {
        double total = 0;

        for (OrderItem item : orderItems) {
            total += item.getTotalPrice();
        }

        return total;
    }

    //method เปลี่ยนสถานะของ Order นี้
    public void updateStatus(OrderStatus newStatus) {

         //คุมไม่ให้อัพเดทสถานะย้อนกลับ
        if (this.status == OrderStatus.Pending && newStatus == OrderStatus.Received) {
            this.status = newStatus;
        } 
        else if (this.status == OrderStatus.Received && newStatus == OrderStatus.Cooking) {
            this.status = newStatus;
        } 
        else if (this.status == OrderStatus.Cooking && newStatus == OrderStatus.Ready) {
            this.status = newStatus;
        } 
        else {
            System.out.println("เปลี่ยนสถานะไม่ถูกต้อง");
        }

    }

    //method cancel order นี้ และเปลี่ยนสถานะเป็น Cancelled
    public void cancelOrder() {
        this.status = OrderStatus.Cancelled;
    }

    //method get ต่างๆที่เอาขอค่าของ class Order
    public int getOrderID() { return orderID; } //ขอ ID ของการสั่งอาหารครั้งนี้
    public Customer getCustomer() { return  customer;} //ขอ Class Customer ของลูกค้าที่จะสั่งครั้งนี้
    public Store getStore() { return  store; } //ขอ Class Store ที่จะสั่งอาหารครั้งนี้
    public Payment getPayment() {return payment;} //ขอ Class Payment ของรายการสั้งอาหารครั้งนี้
    public List<OrderItem> getOrderItemList() { return  orderItems; } //ของลิสรายการของ Class OrderItem เพื่อดูว่ามี Product อะไรบ้าง จำนวนเท่าไหร่ ราคาเท่าไหร่ในรายการสั่งซื้อครั้งนี้
    public int getOrderItemListNum() {return  orderItems.size();} //ขอจำนวนของ ลิส OrderItem ว่ามีกี่อย่าง
    public OrderStatus getOrderStatus() { return  status;} //ขอสถานะของ Order นี้
    
}
