package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.OpeningHours;


public interface  OpeningHoursRepository extends JpaRepository<OpeningHours, Integer> {
    
    OpeningHours  findByOpeningHoursID(int openingHoursID);
}
