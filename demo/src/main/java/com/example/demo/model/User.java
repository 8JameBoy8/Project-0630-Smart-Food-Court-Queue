package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

// คลาสแม่ (Superclass) สำหรับผู้ใช้ และต้องเปลี่ยนเป็น abstract ด้วย
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name = "users")
public abstract  class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int userID;

    protected String userName;
    protected String email;
    protected String password;

    public User() {};

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
    //contructor ที่ใส่ข้อมูล IDผุ้ใช้ ชื่อผู้ใช้ อีเมล รหัสผ่าน เพื่อสร้าง Class User แต่จะไม่สร้่าง Class นี้ตรงๆแต่ให้สร้าง sub Class แทน
    public User(int userID, String userName, String email, String password) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public void setUserName(String name) {this.userName = name;}
    public void setEmail(String email) {this.email = email;}
    public void setPassword(String password) {this.password = password;}
    
   //method get ต่างๆที่เอาขอค่าของ class User และ sub Class ของมันด้วย
    public int getuserID() {return  userID;} //ขอ ID ของ User คนนี้
    public String getName() { return userName; } //ขอชื่อ USer คนนี้
    public String getPassword() {return password; } //ขอรหัสผ่านของ User คนนี้
    public String getEmail() { return  email; } //ขออีเมลของ User คนนี้


}