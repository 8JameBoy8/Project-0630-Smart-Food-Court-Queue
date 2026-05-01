package com.example.demo.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "stores")
public class Store {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int storeID;

    private String storeName;

    @OneToOne
    private Staff staff;

    @ManyToOne
    private Canteen canteen;

    @OneToMany(mappedBy = "store")
    private List<Product> productsList; //ลิสเก็บ class product ที่ขายของร้านค้า

    @OneToMany(mappedBy = "store")
    private List<Order> orderQueue; //ลิสเก็บคิว order ที่ถูกสั่งมาให้ร้านนี้

    @Transient
    private Map<DayOfWeek, OpeningHours> openingHoursMap = new HashMap<>(); //ใช้ map เพื่อเด็บ key เป็นชื่อวัน และ value เป็น class OpeningHours ที่เป็นเวลาเปิด-ปิด

    //contructor ที่ใส่ข้อมูล id ชื่อ Staff ที่เป็นเจ้าชองร้าน เพื่อสร้าง Class Store และสร้างลิสรายการเพื่อเก็บ Product ที่มีอยู่ใน Store นี้ และ ลิสคิวที่เก็บ Order ที่ถูกสั่งมาให้ร้านนี้
    public Store(int storeID, String storeName,Staff staff,Canteen canteen) {
        this.storeID = storeID;
        this.storeName = storeName;
        this.staff = staff;
        this.canteen = canteen;
        this.productsList = new ArrayList<>();
        this.orderQueue = new LinkedList<>();
    }

    public void addProduct(int id, String name, int price) {
        Product p = new Product (id,name,this,price);
        productsList.add(p);
    }

    public void setProductStatus(Product product, boolean status) {
       
        if(productsList.contains(product)) {
            product.setAvailable(status);
        }
        else {
            System.out.println("Product นี้ไม่ได้อยู่ในร้านนี้");
        }
    }

    public void addOrder(Order order) {
        orderQueue.add(order);
    }


    //method เพื่มเวลาปิดเปิดในวันนั้น
    public void setOpeningHours(DayOfWeek day, OpeningHours hours) {
         openingHoursMap.put(day, hours);
    }

    //เช็คว่าเถึงเวลาเปิดรึยัง
    public boolean isOpen() {
    DayOfWeek today = LocalDate.now().getDayOfWeek();  //รับชื่อวันนี้

    OpeningHours hours = openingHoursMap.get(today);  //เอาวันที่รับมาที่เป็น key ไปใช้ขอ value เวลาปิดเปิดของวันนั้น

    if (hours == null) {
        return false; // ไม่มีข้อมูล = ปิด
    }

    return hours.isOpenNow(); //เรียก method เพื่อเช็ค
}

   //ตั้งค่าเวลาเปิดวันปกติทีเดียว
    public void setWeekdayHours(OpeningHours hours) {
    for (DayOfWeek day : DayOfWeek.values()) {
        if (day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY) {
            openingHoursMap.put(day, hours);
             }
        }
    }

    //method get ต่างๆที่เอาขอค่าของ class Store
    public Staff geStaff() {return  staff;} //ขอ Class Staff ที่เป็รเจ้าของ Store นี้
    public int getStoreID() {return  storeID;} //ขอ ID ของ Store
    public String getStoreName() {return  storeName;} //ขอชื่อ Store
    public List<Product> getProductList() { return productsList; } //ของลิสรายการโปรดักในร้านทั้งหมด
    public List<Order> getOrderQueue() { return orderQueue; } //ชอลิสคิว Order ของร้านทั้งหมด

    
}