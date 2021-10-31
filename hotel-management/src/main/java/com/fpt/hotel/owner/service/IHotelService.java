package com.fpt.hotel.owner.service;

import com.fpt.hotel.model.Hotel;
import com.fpt.hotel.owner.dto.response.HotelResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IHotelService {

    List<HotelResponse> findAllHotels();

    Hotel createHotel(String folder, String hotel, List<MultipartFile>  files);

    Hotel updateHotel(Long id ,String folder, String hotel, List<MultipartFile>  files);
}
