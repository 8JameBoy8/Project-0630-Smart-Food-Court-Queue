package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Canteen;

public interface  CanteenRepository extends JpaRepository<Canteen, Integer>{
    
    Canteen findByCanteenID(int canteenID);
}
