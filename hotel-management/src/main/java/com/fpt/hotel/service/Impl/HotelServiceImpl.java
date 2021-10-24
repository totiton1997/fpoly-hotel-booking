package com.fpt.hotel.service.Impl;

import com.fpt.hotel.model.Hotel;
import com.fpt.hotel.repository.HotellRepository;
import com.fpt.hotel.service.IHotelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImpl implements IHotelService {

    Logger logger = LoggerFactory.getLogger(HotelServiceImpl.class);

    @Autowired
    private HotellRepository hotellRepository;

    @Override
    public List<Hotel> findAllHotels() {

        logger.info("Find all data: "+hotellRepository.findAll());
        return hotellRepository.findAll();
    }

    @Override
    public Hotel createHotel(Hotel hotel) {

        return hotellRepository.save(hotel);
    }
}
