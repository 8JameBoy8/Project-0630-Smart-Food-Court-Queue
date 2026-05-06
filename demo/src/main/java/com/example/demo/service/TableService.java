package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.model.Canteen;
import com.example.demo.model.Customer;
import com.example.demo.model.Reservation;
import com.example.demo.model.table;
import com.example.demo.repository.CanteenRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.tableRepository;

@Service
public class TableService {
    private final ReservationRepository reservationRepository;
    private final tableRepository tableRepository;
    private final CanteenRepository canteenRepository;
    private final CustomerRepository customerRepository;

    public TableService(
        tableRepository tableRepository, 
        CanteenRepository canteenRepository, 
        CustomerRepository customerRepository, ReservationRepository reservationRepository
    ) 
    {
        this.tableRepository = tableRepository;
        this.canteenRepository = canteenRepository;
        this.customerRepository = customerRepository;
        this.reservationRepository = reservationRepository;
    }

    //  หาโต๊ะ
    private table findTableByNo(int tableNo,int canteenId) {
        Canteen canteen = canteenRepository.findByCanteenID(canteenId);
        for (table t : tableRepository.findByCanteen_CanteenID(canteenId)) {
            if (t.getTableNo() == tableNo) return t;
        }
        return null;
    }

    public Map<String, Object> findTableNum(int canteenId) {
        Canteen canteen = canteenRepository.findByCanteenID(canteenId);

        return Map.of("TableNum", canteen.getTableNum());
    }

    //GET: สถานะโต๊ะ
    public Map<String, Object> getTableStatus(int canteenId) {
        removeExpiredReservations();
        Canteen canteen = canteenRepository.findByCanteenID(canteenId);

        List<Integer> bookedTables = new ArrayList<>();

        for (table table : canteen.getTableList()) {
            if (!table.isAvailable()) {
                bookedTables.add(table.getTableID());
            }
        }

        Map<String, Object> res = new HashMap<>();
        res.put("totalTables", canteen.getTableList().size());
        res.put("bookedTablesID", bookedTables);
    
        return res;
    }

     //POST: จองโต๊ะ
    public Map<String, Object> bookTable(int userId, int tableNo, int duration,int canteenId) {

        if (duration <= 0) {
            return Map.of("error", "duration ต้องมากกว่า 0");
        }

        Customer customer = customerRepository.findByUserID(userId);

        // เช็คว่ามี reservation ที่ยัง active อยู่ไหม
        boolean hasActiveReservation = reservationRepository.existsByCustomerAndIsActiveTrue(userId);
    
        if (hasActiveReservation) {
            return Map.of("success", false, "message", "มีการจองโต๊ะอยู่แล้ว");
        }

        if (customer == null) {
            return Map.of("success", false, "message", "ไม่พบผู้ใช้");
        }

        table table = tableRepository.findByCanteen_CanteenIDAndTableNo(canteenId, tableNo);
        if (table == null) {
            return Map.of("success", false, "message", "ไม่พบโต๊ะ");
        }

        if (!table.isAvailable()) {
            return Map.of("success", false, "message", "โต๊ะไม่ว่าง");
        }

        Reservation r = new Reservation(customer,table, duration);


        if (r != null) {
          reservationRepository.save(r);
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

    Reservation r = reservationRepository.findByReservationId(reservationId);

        if (r == null) {
            return Map.of("success", false, "message", "ไม่พบ reservation");
        }

        if (!r.isActive()) {
            return Map.of("success", false, "message", "ว่างอยู่แล้ว");
        }

        r.cancel(); // คืนโต๊ะ
        reservationRepository.save(r);

        return Map.of("success", true, "message", "ยกเลิกสำเร็จ");
    }

    //method เช็คโต๊ะที่หมดเวลา
    public void removeExpiredReservations() {

    List<Integer> toRemove = new ArrayList<>();

    for (Reservation r : reservationRepository.findAll()) {
        if (r.isExpired()) {
            r.cancel(); // คืนโต๊ะ
            toRemove.add(r.getReservationID());
        }
    }

    for (int id : toRemove) {
        reservationRepository.deleteById(id);;
    }
    }
}