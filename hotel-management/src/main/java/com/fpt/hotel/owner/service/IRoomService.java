package com.fpt.hotel.owner.service;

import com.fpt.hotel.model.Room;
import com.fpt.hotel.owner.dto.RoomDTO;

import java.util.List;

public interface IRoomService {
    List<RoomDTO> findAll();

    RoomDTO save(Room room);

}
