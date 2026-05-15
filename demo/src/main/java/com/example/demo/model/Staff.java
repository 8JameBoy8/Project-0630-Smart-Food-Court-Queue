package com.example.demo.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.Entity ;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "staffs")
public class Staff extends User {

    @OneToOne
    @JoinColumn(name ="StoreID")
     private Store store;

    public Staff() {}

    public Staff(String userName, String email, String password) {
        super(userName, email, password);
    }

    public Staff(String userName, String email, String password, Store store) {
        super(userName, email, password);
        this.store = store;
    }

    //contructor ที่ใส่ข้อมูล contructor ทั้งหมดของ Super Class user เพื่อสร้าง sub Class Staff และเพิ่ม Class Store ให้กับ Staff เพื่อเรียกใช้ method store
    //ที่ staff เป็นเจ้าของ
    public Staff(int userID, String userName, String email, String password, Store store) {
        super(userID, userName, email, password);
        this.store = store;
    }

    @Override
    public String getRole() {
        return "staff";
    }

    @Override
    public List<String> getPermissions() {
        return List.of("view_orders", "update_order_status", "verify_payment", "manage_menu");
    }

    @Override
    public Map<String, Object> getProfileInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("userId", userID);
        info.put("name", userName);
        info.put("email", email);
        info.put("role", getRole());
        info.put("storeId", store != null ? store.getStoreID() : null);
        info.put("storeName", store != null ? store.getStoreName() : null);
        return info;
    }
    public void setStore(Store store) {this.store = store;}
    //method get ต่างๆที่เอาขอค่าของ class Staff
    public Store getStore() {return store;} //ขอ Class Store ที่ Staff คนนี้เป็นเจ้าของ
}