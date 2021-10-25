package com.fpt.hotel.repository;

import com.fpt.hotel.model.Transaction_Info;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionInfoRepository extends JpaRepository<Transaction_Info , Long> {
}
