package com.fpt.hotel.owner.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpt.hotel.model.Hotel;
import com.fpt.hotel.owner.service.IHotelService;
import com.fpt.hotel.repository.HotelRepository;
import com.fpt.hotel.service.FileManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class HotelServiceImpl implements IHotelService {

    Logger logger = LoggerFactory.getLogger(HotelServiceImpl.class);
    @Autowired
    FileManagerService fileManagerService;
    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public List<Hotel> findAllHotels() {

        logger.info("Find all data: " + hotelRepository.findAll());
        return hotelRepository.findAll();
    }

    @Override
    public Hotel createHotel(String folder, String hotel, List<MultipartFile> files) {
        Hotel hotelJson = new Hotel();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            hotelJson = objectMapper.readValue(hotel, Hotel.class);
            String fileName = "";
            Boolean existsByName = hotelRepository.existsByName(hotelJson.getName());

            if (existsByName) {
                return null;
            }
            if (files != null) {
                List<String> fileLists = fileManagerService.save(folder, files);
                fileName = fileLists.get(0);
            }

            hotelJson.setImages(fileName);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return hotelRepository.save(hotelJson);
    }

    @Override
    public Hotel updateHotel(Long id, String folder, String hotel, List<MultipartFile> files) {
        Hotel hotelOld = hotelRepository.findById(id).get();
        Hotel hotelJson = new Hotel();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            hotelJson = objectMapper.readValue(hotel, Hotel.class);

            // lấy id cũ để cập nhật
            hotelJson.setId(hotelOld.getId());

            String image = "";
            if (files == null) {
                image = hotelOld.getImages();
            } else {
                List<String> fileLists = fileManagerService.save(folder, files);
                String fileName = fileLists.get(0);
                fileManagerService.delete(folder, image);
                image = fileName;
            }

            hotelJson.setImages(image);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return hotelRepository.save(hotelJson);
    }


}
