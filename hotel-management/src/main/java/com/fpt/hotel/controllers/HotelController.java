package com.fpt.hotel.controllers;


import com.fpt.hotel.model.Hotel;
import com.fpt.hotel.payload.response.ResponseObject;
import com.fpt.hotel.service.Impl.HotelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/hotels")
public class HotelController {

    @Autowired
    private HotelServiceImpl hotelService;

    @GetMapping
    public ResponseEntity<?> findAllHotels() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(HttpStatus.OK.toString(), "Hiển thi tất cả các cơ sở!", hotelService.findAllHotels())
        );
    }

    @PostMapping
    public ResponseEntity<?> createHotel(@RequestBody Hotel hotelDTO) {


        Hotel hotel = hotelService.createHotel(hotelDTO);
        if (hotel != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(HttpStatus.OK.toString(), "Tạo mới cơ sở hotel thành công!", hotel)
            );
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Tạo mới cơ sở hotel không thành công!", hotel)
        );


    }

}
