package com.example.demo.model;
public class Staff extends User {
     private Store store;

    //contructor ที่ใส่ข้อมูล contructor ทั้งหมดของ Super Class user เพื่อสร้าง sub Class Staff และเพิ่ม Class Store ให้กับ Staff เพื่อเรียกใช้ method store
    //ที่ staff เป็นเจ้าของ
    public Staff(int userID, String userName, String email, String password, Store store) {
        super(userID, userName, email, password);
        this.store = store;
    }

    //method เริ่มทำอาหาร
    public void startCooking() {
        Order order = store.peekOrder(); //ขอดูและรับคิว order ล่าสุดใน store ที่เป็นเจ้าของ

        if (order != null) {
            order.updateStatus(Order.OrderStatus.Cooking); //เปลียนสถานะของ order นั้นเป็น cooking
        }
    }

    //method เสร็จทำอาหาร
    public void finishOrder() {
        Order order = store.peekOrder(); //ขอดูและรับคิว order ล่าสุดใน store ที่เป็นเจ้าของ

        if (order != null) {
            order.updateStatus(Order.OrderStatus.Ready); //เปลียนสถานะของ order นั้นเป็น Ready
        }
    }

    //method เรียก order ถัดไปหลังจากคิวล่าสุด
    public void callNextOrder() {
        store.removeReadyOrder(); //เรียกใช้ method เพื่อเอาคิวล่าสุดออก
    }

    //method ให้ staff ยื่นยันการจ่ายเงินเอง
    public void verifyPayment(Payment payment, boolean isValid) {
        if (isValid) {
            payment.verify();
        }else {
            payment.reject();
        }
    }

    //method get ต่างๆที่เอาขอค่าของ class Staff
    public Store getStore() {return store;} //ขอ Class Store ที่ Staff คนนี้เป็นเจ้าของ
}