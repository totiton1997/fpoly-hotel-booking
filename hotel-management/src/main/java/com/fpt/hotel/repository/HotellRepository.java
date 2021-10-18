package com.fpt.hotel.repository;

import com.fpt.hotel.model.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotellRepository extends JpaRepository<Facility, Long> {
}
