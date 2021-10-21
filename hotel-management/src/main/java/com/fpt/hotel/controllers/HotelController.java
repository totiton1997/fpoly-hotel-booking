package com.fpt.hotel.controllers;


import com.fpt.hotel.payload.response.ResponseObject;
import com.fpt.hotel.service.Impl.HotelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    @Autowired
    private HotelServiceImpl hotelService;

    @GetMapping
    public ResponseEntity<?> findAllHotels(){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok","Hiển thi tất cả các cơ sở!",hotelService.findAllHotels())
        );
    }

}
