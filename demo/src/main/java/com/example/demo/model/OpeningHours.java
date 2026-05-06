package com.example.demo.model;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "opening_hours")
public class OpeningHours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int openingHoursID;

    private DayType dayType; // "WEEKDAY" หรือ "EVERYDAY"
    private LocalTime openTime;  // เวลาเปิด เช่น 08:00
    private LocalTime closeTime; // เวลาปิด เช่น 17:00

    public enum DayType {
        WEEKDAY,   // วันธรรมดา จันทร์-ศุกร์
        EVERYDAY   // ทุกวัน
    }

    @ManyToOne
    @JoinColumn(name = "canteenID")
    private Canteen canteen;

    public OpeningHours() {}

    public OpeningHours(DayType dayType, LocalTime openTime, LocalTime closeTime) {
        this.dayType = dayType;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public void setCanteen(Canteen canteen) {this.canteen = canteen;}

    public int getOpeningHoursID() { return openingHoursID; }
    public DayType getDayType() { return dayType; }
    public LocalTime getOpenTime() { return openTime; }
    public LocalTime getCloseTime() { return closeTime; }
    public Canteen getCanteen() { return canteen; }
}