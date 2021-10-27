package com.fpt.hotel.owner.impl;

import com.fpt.hotel.model.Room;
import com.fpt.hotel.owner.dto.RoomDTO;
import com.fpt.hotel.owner.service.IRoomService;
import com.fpt.hotel.repository.RoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements IRoomService {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<RoomDTO> findAll() {
       return roomRepository.findAll().stream().map(
               room -> modelMapper.map(room , RoomDTO.class)).collect(Collectors.toList());
    }

    @Override
    public RoomDTO save(Room room) {

        boolean check = roomRepository.existsByNumberRoom(room.getNumberRoom());
        if(check){
            return null;
        }

        Room newRoom = roomRepository.save(room);
        return modelMapper.map(newRoom, RoomDTO.class);
    }
}
