package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "toppings")
public class Topping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int toppingID;

    private String toppingName;
    private int extraPrice;
    private boolean isAvailable;

    public Topping() {}

    public Topping(String toppingName, int extraPrice) {
        this.toppingName = toppingName;
        this.extraPrice = extraPrice;
        this.isAvailable = true;
    }

    public void setAvailable(boolean isAvailable) { this.isAvailable = isAvailable; }
    public void setToppingName(String name) { this.toppingName = name; }
    public void setExtraPrice(int price) { this.extraPrice = price; }

    public int getToppingID() { return toppingID; }
    public String getToppingName() { return toppingName; }
    public int getExtraPrice() { return extraPrice; }
    public boolean isAvailable() { return isAvailable; }
}