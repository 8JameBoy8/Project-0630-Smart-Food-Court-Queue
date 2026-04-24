package com.example.demo.model;
public class Table {
    private Canteen canteen;//class canteen
    private int tableID;
    private int tableNo; //หมายเลขโต๊ะ
    private boolean isAvailable; //สถานะว่างหรือไม่

    //contructor ที่ใส่ข้อมูล โรงอาหาร IDโต๊ะ หมายเลขโต๊ะ เพื่อสร้าง Class Table
    public Table(Canteen canteenName, int tableID, int tableNo) {   
        this.canteen = canteenName;
        this.tableID = tableID;
        this.tableNo = tableNo;
        
    }

    //method เปลี่ยนสถานะของโต๊ะ
    public void setAvailable(boolean status) {
    this.isAvailable = status;
}

    //method get ต่างๆที่เอาขอค่าของ class Table
    public Canteen getCanteen() { return canteen;} //ของโรงอาหารที่โต๊ะนี้อยู่
    public int getTableID() { return  tableID;} //ขอ ID ของโต๊ะนี้
    public int getTableNo() { return  tableNo; } //ขอหมายเลขของโต๊ะนี้ในโรงอาหารนั้น
    public  boolean  isAvailable() { return  isAvailable;} //ขอถามว่าโต๊ะนี้สามารถจองได้ไหมหรือว่ากำลังถูกจองอยู่
}