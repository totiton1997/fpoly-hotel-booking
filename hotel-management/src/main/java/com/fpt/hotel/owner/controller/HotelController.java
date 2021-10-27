package com.fpt.hotel.owner.controller;


import com.fpt.hotel.model.Hotel;
import com.fpt.hotel.payload.response.ResponseObject;
import com.fpt.hotel.owner.impl.HotelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("*")
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/hotels")
public class HotelController {

    @Autowired
    private HotelServiceImpl hotelService;

    @GetMapping
    public ResponseEntity<ResponseObject> findAllHotels() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(HttpStatus.OK.toString(), "Hiển thi tất cả các cơ sở!", hotelService.findAllHotels())
        );
    }

    @PostMapping(value = "/{folder}", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseObject> createHotel(@PathVariable("folder") String folder, @RequestPart("hotel") String hotel,
                                         @RequestPart("file") MultipartFile[] files) {


        Hotel newHotel = hotelService.createHotel(folder, hotel, files);
        if (newHotel != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(HttpStatus.OK.toString(), "Tạo mới cơ sở hotel thành công!", newHotel)
            );
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Thêm cơ sở thất bại!", newHotel)
        );
    }

    @PutMapping(value = "{id}/{folder}", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseObject> updateHotel(@PathVariable("id") Long id ,@PathVariable("folder") String folder, @RequestPart("hotel") String hotel,
                                         @RequestPart(name = "file" , required = false) MultipartFile[] files ) {

        Hotel newHotel = hotelService.updateHotel(id , folder, hotel, files);
        if (newHotel != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(HttpStatus.OK.toString(), "Cập nhật cơ sở hotel thành công!", newHotel)
            );
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Cập nhật cơ sở thất bại!", newHotel)
        );
    }

}
