package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ReservationService;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // GET การจองปัจจุบันของ user
    @GetMapping("/users/{id}/reservation")
    public Map<String, Object> getReservation(@PathVariable int id) {
        return reservationService.getActiveReservation(id);
    }

    // GET ประวัติการจองทั้งหมดของ user
    @GetMapping("/users/{id}/reservations")
    public List<Map<String, Object>> getReservationHistory(@PathVariable int id) {
        return reservationService.getReservationHistory(id);
    }
}