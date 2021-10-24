package com.fpt.hotel.repository;

import com.fpt.hotel.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query(value = "SELECT * FROM `bookings` WHERE id_user = ?1 and status = \"Đang sử dụng\"" , nativeQuery = true)
    List<Booking> findById_user (Long id);
}
