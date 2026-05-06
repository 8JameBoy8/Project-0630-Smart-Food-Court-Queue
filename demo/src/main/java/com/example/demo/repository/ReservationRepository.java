package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Reservation;

public interface  ReservationRepository extends JpaRepository<Reservation, Integer> {
    
    Reservation findByReservationId(int reservationId);

    boolean existsByCustomerAndIsActiveTrue(int CustomerId);

     Reservation findByCustomerUserIDAndIsActiveTrue(int userId);
    List<Reservation> findByCustomerUserID(int userId);
}
