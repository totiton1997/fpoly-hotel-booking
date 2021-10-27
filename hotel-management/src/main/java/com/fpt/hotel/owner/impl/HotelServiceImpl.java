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
    public Hotel createHotel(String folder, String hotel, MultipartFile[] files) {
        Hotel hotelJson = new Hotel();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            hotelJson = objectMapper.readValue(hotel, Hotel.class);

            Boolean existsByUsername = hotelRepository.existsByName(hotelJson.getName());

            if (existsByUsername) {
                return null;
            }

            List<String> fileLists = fileManagerService.save(folder, files);
            String fileName = fileLists.get(0);

            hotelJson.setImages(fileName);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return hotelRepository.save(hotelJson);
    }

    @Override
    public Hotel updateHotel(Long id, String folder, String hotel, MultipartFile[] files) {
        Hotel hotelOld = hotelRepository.findById(id).get();
        Hotel hotelJson = new Hotel();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            hotelJson = objectMapper.readValue(hotel, Hotel.class);

            // lấy id cũ để cập nhật
            hotelJson.setId(hotelOld.getId());
            String imageOld = hotelOld.getImages();

            // không chọn ảnh thì set lại ảnh cũ
            if (files == null || files.length == 0) {
                hotelJson.setImages(imageOld);
            } else {
                List<String> fileLists = fileManagerService.save(folder, files);
                String fileName = fileLists.get(0);
                fileManagerService.delete(folder, imageOld);
                hotelJson.setImages(fileName);
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return hotelRepository.save(hotelJson);
    }


}
