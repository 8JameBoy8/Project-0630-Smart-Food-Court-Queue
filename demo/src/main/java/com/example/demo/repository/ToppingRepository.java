package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Topping;

public interface ToppingRepository extends JpaRepository<Topping, Integer> {
    
    Topping findByToppingID(int ToppingID);

}
