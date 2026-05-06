package com.example.demo.service;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.CustomerRepository;

import com.example.demo.model.Customer;
import com.example.demo.model.Reservation;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;

    public ReservationService(
        ReservationRepository reservationRepository,
        CustomerRepository customerRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.customerRepository = customerRepository;
    }

    // GET การจองที่ยัง active อยู่
    public Map<String, Object> getActiveReservation(int userId) {

        Customer customer = customerRepository.findByUserID(userId);

        if (customer == null) return Map.of("error", "ไม่พบผู้ใช้");

        Reservation r = reservationRepository.findByCustomerUserIDAndIsActiveTrue(userId);

        if (r == null) return Map.of("message", "ไม่มีการจองที่ active อยู่");

        return Map.of(
            "reservationId", r.getReservationID(),
            "tableNo", r.getTable().getTableNo(),
            "canteenName", r.getTable().getCanteen().getCanteenName(),
            "isActive", r.isActive(),
            "isExpired", r.isExpired()
        );
    }

    // GET ประวัติการจองทั้งหมด
    public List<Map<String, Object>> getReservationHistory(int userId) {

        Customer customer = customerRepository.findByUserID(userId);

        if (customer == null) return List.of();

        List<Map<String, Object>> result = new ArrayList<>();

        for (Reservation r : reservationRepository.findByCustomerUserID(userId)) {
            Map<String, Object> data = new HashMap<>();
            data.put("reservationId", r.getReservationID());
            data.put("tableNo", r.getTable().getTableNo());
            data.put("canteenName", r.getTable().getCanteen().getCanteenName());
            data.put("isActive", r.isActive());
            result.add(data);
        }

        return result;
    }
}