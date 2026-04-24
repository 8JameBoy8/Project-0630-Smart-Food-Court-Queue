package com.example.demo.model;
// คลาสแม่ (Superclass) สำหรับผู้ใช้ และต้องเปลี่ยนเป็น abstract ด้วย
public abstract  class User {
    protected int userID;
    protected String userName;
    protected String email;
    protected String password;

    //contructor ที่ใส่ข้อมูล IDผุ้ใช้ ชื่อผู้ใช้ อีเมล รหัสผ่าน เพื่อสร้าง Class User แต่จะไม่สร้่าง Class นี้ตรงๆแต่ให้สร้าง sub Class แทน
    public User(int userID, String userName, String email, String password) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

   //method get ต่างๆที่เอาขอค่าของ class User และ sub Class ของมันด้วย
    public int getuserID() {return  userID;} //ขอ ID ของ User คนนี้
    public String getName() { return userName; } //ขอชื่อ USer คนนี้
    public String getPassword() {return password; } //ขอรหัสผ่านของ User คนนี้
    public String getEmail() { return  email; } //ขออีเมลของ User คนนี้

    
}