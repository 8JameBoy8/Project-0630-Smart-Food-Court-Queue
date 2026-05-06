package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tables")
public class table {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tableID;

    private int tableNo; //หมายเลขโต๊ะ

    @ManyToOne
    @JoinColumn(name = "canteenID")
    private Canteen canteen;//class canteen
    private boolean isAvailable; //สถานะว่างหรือไม่

    public table() {}

    public table(Canteen canteenName, int tableNo) {   
        this.canteen = canteenName;
        this.tableNo = tableNo;
    }

    //contructor ที่ใส่ข้อมูล โรงอาหาร IDโต๊ะ หมายเลขโต๊ะ เพื่อสร้าง Class Table
    public table(Canteen canteenName, int tableID, int tableNo) {   
        this.canteen = canteenName;
        this.tableID = tableID;
        this.tableNo = tableNo;
        
    }

    //method เปลี่ยนสถานะของโต๊ะ
    public void setAvailable(boolean status) {
        this.isAvailable = status;
    }

    public void setCanteen(Canteen canteen) {this.canteen = canteen;}
    public void setTableNo(int tableNo) {this.tableNo = tableNo;}
    
    //method get ต่างๆที่เอาขอค่าของ class Table
    public Canteen getCanteen() { return canteen;} //ของโรงอาหารที่โต๊ะนี้อยู่
    public int getTableID() { return  tableID;} //ขอ ID ของโต๊ะนี้
    public int getTableNo() { return  tableNo; } //ขอหมายเลขของโต๊ะนี้ในโรงอาหารนั้น
    public  boolean  isAvailable() { return  isAvailable;} //ขอถามว่าโต๊ะนี้สามารถจองได้ไหมหรือว่ากำลังถูกจองอยู่
}