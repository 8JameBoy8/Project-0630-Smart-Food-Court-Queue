package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.table;

public interface tableRepository extends JpaRepository<table, Integer> {

    // ดึง table ของโรงอาหาร เรียงตาม ID
    List<table> findByCanteen_CanteenIDOrderByTableID(int canteenID);

    table findByCanteen_CanteenIDAndTableNo(int canteenID, int tableNo);
    List<table> findByCanteen_CanteenID(int canteenID);

}