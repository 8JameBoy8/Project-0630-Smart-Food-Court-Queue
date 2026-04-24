package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.model.Canteen;
import com.example.demo.model.Table;

@Service
public class TableService {

    private Canteen canteen;

    public TableService() {
        // 🔥 mock data ก่อน (เดี๋ยวค่อยเชื่อมจริง)
        canteen = new Canteen(1, "Main Canteen");

        for (int i = 1; i <= 20; i++) {
            Table t = new Table(canteen, i, i);

            if (i == 3 || i == 7 || i == 12) {
                t.setAvailable(false);
            } else {
                t.setAvailable(true);
            }

            canteen.addTable(t);
        }
    }

    public Map<String, Object> getTableStatus() {

        List<Integer> bookedTables = new ArrayList<>();

        for (Table table : canteen.getTableList()) {
            if (!table.isAvailable()) {
                bookedTables.add(table.getTableNo());
            }
        }

        Map<String, Object> res = new HashMap<>();
        res.put("totalTables", canteen.getTableNum());
        res.put("bookedTables", bookedTables);

        return res;
    }
}