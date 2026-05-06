package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderID;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Store store;

    @OneToOne
    @JoinColumn(name = "paymentID")
    private Payment payment;

     @OneToMany (mappedBy = "order")
    private  List<OrderItem> orderItems;  //ลิสเก็บ orderItem ที่เก็บ product และจำนวน

    private String note; 
    int totalPrice = 0;
    private OrderStatus status; //สถานะของ order

    public enum OrderStatus {
        Pending,
        Received,
        Cooking,
        Ready,
        Cancelled
    }

    public Order(){
        this.orderItems = new ArrayList<>();
        this.status = OrderStatus.Pending;
    }

     public Order(Customer customer,Store store){
        this.customer = customer;
        this.store = store;
        this.orderItems = new ArrayList<>();
        this.status = OrderStatus.Pending;
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
      public void addItem(Product product, int quantity, List<Topping> selectedToppings) {
        for (OrderItem item : orderItems) {
            if (item.getProduct().equals(product)) {
                item.setQuantity(quantity);
                return;
            }
        }
        OrderItem newItem = new OrderItem(this, product, quantity);
        selectedToppings.forEach(newItem::addTopping);
        orderItems.add(newItem);
    }

    // addItem แบบไม่มี topping (เผื่อกรณีไม่เลือก)
    public void addItem(Product product, int quantity) {
        addItem(product, quantity, new ArrayList<>());
    }

    //method ลบอาหารที่อยู่ในลิส
     public void removeItem(Product product) {
        orderItems.removeIf(item -> item.getProduct().equals(product)); //รับค่า product ที่จะลบแล้วเช็คว่ามี product ไหรในลิสคือ product นั้นและลบออก
    }

    //method คำนวนเงินของทุก orderItem ในลิส
    public double calculateTotalPrice() {
        totalPrice = 0; // reset ก่อนเสมอ
        for (OrderItem item : orderItems) {
            totalPrice += item.getTotalPrice();
        }

        return totalPrice;
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

    public void setCustomer(Customer customer) {this.customer = customer;}
    public void setStore(Store store) {this.store = store;}
    public void setNote(String note) { this.note = note; }
    
    //method get ต่างๆที่เอาขอค่าของ class Order
    public int getOrderID() { return orderID; } //ขอ ID ของการสั่งอาหารครั้งนี้
    public Customer getCustomer() { return  customer;} //ขอ Class Customer ของลูกค้าที่จะสั่งครั้งนี้
    public Store getStore() { return  store; } //ขอ Class Store ที่จะสั่งอาหารครั้งนี้
    public Payment getPayment() {return payment;} //ขอ Class Payment ของรายการสั้งอาหารครั้งนี้
    public List<OrderItem> getOrderItemList() { return  orderItems; } //ของลิสรายการของ Class OrderItem เพื่อดูว่ามี Product อะไรบ้าง จำนวนเท่าไหร่ ราคาเท่าไหร่ในรายการสั่งซื้อครั้งนี้
    public int getOrderItemListNum() {return  orderItems.size();} //ขอจำนวนของ ลิส OrderItem ว่ามีกี่อย่าง
    public OrderStatus getOrderStatus() { return  status;} //ขอสถานะของ Order นี้
    public int getTotalPrice() {return totalPrice;}
    public String getNote() { return note; }
}
