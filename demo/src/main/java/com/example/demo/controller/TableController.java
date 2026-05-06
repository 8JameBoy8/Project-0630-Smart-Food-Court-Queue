package com.example.demo.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.TableService;

@RestController
@CrossOrigin
@RequestMapping("/api/canteens")  // เปลี่ยน prefix
public class TableController {

    private final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    // GET โต๊ะ 
    @GetMapping("/{canteenId}/tables")
    public Map<String, Object> getTablesStatus(@PathVariable int canteenId) {
        tableService.removeExpiredReservations();
        return tableService.getTableStatus(canteenId);
    }

    // POST จองโต๊ะ 
    @PostMapping("/{canteenId}/book")
    public Map<String, Object> book(@RequestBody Map<String, Integer> data,@PathVariable int canteenId) {
        tableService.removeExpiredReservations();
        return tableService.bookTable(
            data.get("userId"),
            data.get("tableNo"),
            data.get("duration"),
            canteenId
        );
    }

    // POST ยกเลิกจอง
    @PostMapping("/cancelBooked")
    public Map<String, Object> cancelBooked(@RequestBody Map<String, Integer> data) {
        return tableService.cancelReservation(data.get("reservationId"));
    }
}
