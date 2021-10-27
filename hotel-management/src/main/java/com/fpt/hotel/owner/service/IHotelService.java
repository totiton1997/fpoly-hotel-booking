package com.fpt.hotel.owner.service;

import com.fpt.hotel.model.Hotel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IHotelService {

    List<Hotel> findAllHotels();

    Hotel createHotel(String folder, String hotel, List<MultipartFile>  files);

    Hotel updateHotel(Long id ,String folder, String hotel, List<MultipartFile>  files);
}
