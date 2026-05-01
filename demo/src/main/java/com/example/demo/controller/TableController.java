package com.example.demo.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.TableService;

@RestController
@CrossOrigin
public class TableController {

    private final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    // GET โต๊ะ
    @GetMapping("/api/{canteenId}/tables")
    public Map<String, Object> getTables(@PathVariable int canteenId) {
        tableService.removeExpiredReservations(); //เช็คโต๊ะที่หมดเวลาจองก่อนให้สถานะของโต๊ะ
        return tableService.getTableStatus(canteenId);
    }

     // POST จองโต๊ะ
    @PostMapping("/api/{canteenId}/book")
    public Map<String, Object> book(@RequestBody Map<String, Integer> data,@PathVariable int canteenId) {
        tableService.removeExpiredReservations(); //เช็คโต๊ะที่หมดเวลาจองก่อนให้จองโต๊ะ

        int userId = data.get("userId");
        int tableNo = data.get("tableNo");
        int duration = data.get("duration");
        
        return tableService.bookTable(userId, tableNo, duration,canteenId);
    }

    // POST ยกเลิกจองโต๊ะ
    @PostMapping("/api/cancelReservation")
    public Map<String, Object> cancel(@RequestBody Map<String, Integer> data) {
        int reservationId = data.get("reservationId");
        return tableService.cancelReservation(reservationId);
}
}