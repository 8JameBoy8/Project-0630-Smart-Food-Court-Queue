package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import com.example.demo.model.Canteen;
import com.example.demo.model.Customer;
import com.example.demo.model.Reservation;
import com.example.demo.model.table;

@Service
public class TableService {

    private Map<Integer, Canteen> canteens = new HashMap<>();
    private final AtomicInteger reservationIdGen = new AtomicInteger(1);
    private Map<Integer, Reservation> reservations = new HashMap<>();
    private final Map<Integer, Customer> customers = new HashMap<>();   // เก็บลูกค้าแบบง่าย ๆ (demo)

    public TableService() {
        
        Canteen canteen = new Canteen(1, "Main Canteen");

        // สร้างโต๊ะ 1–20
        for (int i = 1; i <= 20; i++) {
            table t = new table(canteen, i, i);
            t.setAvailable(true);
            canteen.addTable(t);
        }
        canteens.put(1, canteen);

        // mock ลูกค้า 1 คน (เดี๋ยวค่อยทำ login ทีหลัง)
        Customer c = new Customer(1, "DemoUser", "demo@mail.com", "1234");
        customers.put(1, c);

    }

    //  หาโต๊ะ
    private table findTableByNo(int tableNo,int canteenId) {
        Canteen canteen = findCanteenById(canteenId);
        for (table t : canteen.getTableList()) {
            if (t.getTableNo() == tableNo) return t;
        }
        return null;
    }

    //GET: สถานะโต๊ะ
    public Map<String, Object> getTableStatus(int canteenId) {
        removeExpiredReservations();
        Canteen canteen = findCanteenById(canteenId);

        List<Integer> bookedTables = new ArrayList<>();

        for (table table : canteen.getTableList()) {
            if (!table.isAvailable()) {
                bookedTables.add(table.getTableNo());
            }
        }

        Map<String, Object> res = new HashMap<>();
        res.put("totalTables", canteen.getTableList().size());
        res.put("bookedTables", bookedTables);
    
        return res;
    }

     //POST: จองโต๊ะ
    public Map<String, Object> bookTable(int userId, int tableNo, int duration,int canteenId) {

        Customer customer = customers.get(userId);
        if (customer == null) {
            return Map.of("success", false, "message", "ไม่พบผู้ใช้");
        }

        table table = findTableByNo(tableNo,canteenId);
        if (table == null) {
            return Map.of("success", false, "message", "ไม่พบโต๊ะ");
        }

        if (!table.isAvailable()) {
            return Map.of("success", false, "message", "โต๊ะไม่ว่าง");
        }

        int reservationId = reservationIdGen.getAndIncrement();

        Reservation r = customer.reserveTable(table, duration, reservationId);


        if (r != null) {
          reservations.put(r.getReservationID(), r);
        }

        if (r == null) {
            return Map.of("success", false, "message", "จองไม่สำเร็จ");
        }

        return Map.of(
            "success", true,
            "reservationId", r.getReservationID(),
            "tableNum", table.getTableNo()
        );
    }

    //method ยกเลิกจองโต๊ะ
    public Map<String, Object> cancelReservation(int reservationId) {

    Reservation r = reservations.get(reservationId);

        if (r == null) {
            return Map.of("success", false, "message", "ไม่พบ reservation");
        }

        if (!r.isActive()) {
            return Map.of("success", false, "message", "ถูกยกเลิกไปแล้ว");
        }

        r.cancel(); // คืนโต๊ะ
        reservations.remove(reservationId);

        return Map.of("success", true, "message", "ยกเลิกสำเร็จ");
    }

    //method เช็คโต๊ะที่หมดเวลา
    public void removeExpiredReservations() {

    List<Integer> toRemove = new ArrayList<>();

    for (Reservation r : reservations.values()) {
        if (r.isExpired()) {
            r.cancel(); // คืนโต๊ะ
            toRemove.add(r.getReservationID());
        }
    }

    for (int id : toRemove) {
        reservations.remove(id);
    }
    }

    //helper method หา canteen ด้วย id
    private Canteen findCanteenById(int canteenId) {
        return canteens.get(canteenId);
    }
}