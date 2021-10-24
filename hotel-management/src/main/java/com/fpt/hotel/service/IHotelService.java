package com.fpt.hotel.service;

import com.fpt.hotel.model.Hotel;

import java.util.List;

public interface IHotelService {

    List<Hotel> findAllHotels();

    Hotel createHotel(Hotel hotel);
}
