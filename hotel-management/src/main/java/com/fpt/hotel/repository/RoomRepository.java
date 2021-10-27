package com.fpt.hotel.repository;

import com.fpt.hotel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Boolean existsByNumberRoom(int numberRoom);

    List<Room> findAll();
}
