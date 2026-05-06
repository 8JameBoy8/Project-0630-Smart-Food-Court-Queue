package com.example.demo.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalTime;

import org.springframework.stereotype.Service;
import com.example.demo.model.Canteen;
import com.example.demo.model.OpeningHours;
import com.example.demo.model.table;
import com.example.demo.model.Store;
import com.example.demo.repository.CanteenRepository;
import com.example.demo.repository.StoreRepository;
import com.example.demo.repository.tableRepository;
import com.example.demo.repository.OpeningHoursRepository;


@Service
public class CanteenService {

    private final CanteenRepository canteenRepository;
    private final StoreRepository storeRepository;
    private final OpeningHoursRepository openingHoursRepository;
    private final tableRepository tableRepository;

    public CanteenService(
        CanteenRepository canteenRepository,
        StoreRepository storeRepository,
        OpeningHoursRepository openingHoursRepository,
        tableRepository tableRepository
    ) {
        this.canteenRepository = canteenRepository;
        this.storeRepository = storeRepository;
        this.openingHoursRepository = openingHoursRepository;
        this.tableRepository = tableRepository;
    }

    // GET โรงอาหารทั้งหมด
    public List<Map<String, Object>> getAllCanteens() {

        List<Map<String, Object>> result = new ArrayList<>();

        for (Canteen canteen : canteenRepository.findAll()) {

            Map<String, Object> data = new HashMap<>();
            data.put("canteenId", canteen.getCanteenID());
            data.put("canteenName", canteen.getCanteenName());
            data.put("tableCount", canteen.getTableNum());

            // เวลาเปิดปิด
            List<Map<String, Object>> hours = new ArrayList<>();
            for (OpeningHours h : canteen.getOpeningHours()) {
                Map<String, Object> hourData = new HashMap<>();
                hourData.put("dayType", h.getDayType());
                hourData.put("openTime", h.getOpenTime().toString());
                hourData.put("closeTime", h.getCloseTime().toString());
                hours.add(hourData);
            }
            data.put("openingHours", hours);

            result.add(data);
        }

        return result;
    }

    // GET ร้านในโรงอาหาร
    public List<Map<String, Object>> getStoresByCanteen(int canteenId) {

        Canteen canteen = canteenRepository.findByCanteenID(canteenId);

        if (canteen == null) return List.of();

        List<Map<String, Object>> result = new ArrayList<>();

        for (Store store : canteen.getStoreList()) {
            Map<String, Object> data = new HashMap<>();
            data.put("storeId", store.getStoreID());
            data.put("storeName", store.getStoreName());
            data.put("isOpen", store.getIsOpen());
            result.add(data);
        }

        return result;
    }

    // POST สร้างโรงอาหาร
    public Map<String, Object> createCanteen(String canteenName) {
        Canteen canteen = new Canteen(canteenName);
        canteenRepository.save(canteen);
        return Map.of("canteenId", canteen.getCanteenID(), "canteenName", canteen.getCanteenName());
    }

    // POST ตั้งเวลาเปิดปิด
    public Map<String, Object> setOpeningHours(int canteenId, String dayType, String openTime, String closeTime) {

        Canteen canteen = canteenRepository.findByCanteenID(canteenId);

        if (canteen == null) return Map.of("error", "ไม่พบโรงอาหาร");

        OpeningHours hours = new OpeningHours(
            OpeningHours.DayType.valueOf(dayType),
            LocalTime.parse(openTime),
            LocalTime.parse(closeTime)
        );

        hours.setCanteen(canteen);
        openingHoursRepository.save(hours);

        return Map.of("success", true, "dayType", dayType);
    }

    // POST เพิ่มโต๊ะ
    public Map<String, Object> addTable(int canteenId, int tableNo) {

        Canteen canteen = canteenRepository.findByCanteenID(canteenId);

        if (canteen == null) return Map.of("error", "ไม่พบโรงอาหาร");

        table t = new table(canteen, tableNo);
        tableRepository.save(t);

        return Map.of("success", true, "tableNo", tableNo);
    }
}
