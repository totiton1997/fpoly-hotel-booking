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
    public ResponseEntity<?> findAllHotels(){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(HttpStatus.OK,"Hiển thi tất cả các cơ sở!",hotelService.findAllHotels())
        );
    }

    @PostMapping
    public ResponseEntity<?> createHotel(@RequestBody Hotel hotelDTO){

        if(hotelDTO.getName().isEmpty() || hotelDTO.getName() == null || hotelDTO.getName().length() > 50){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject(HttpStatus.BAD_REQUEST,"Tên hotel không được để trống và nhỏ hơn 50 ký tự!",null)
            );
        }
        else if(hotelDTO.getCity().isEmpty() || hotelDTO.getCity() == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject(HttpStatus.BAD_REQUEST,"Thành phố hotel không được để trống !",null)
            );
        }else if(hotelDTO.getDistrict().isEmpty() || hotelDTO.getDistrict() == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject(HttpStatus.BAD_REQUEST,"Quận/huyện hotel không được để trống!",null)
            );
        }else if(hotelDTO.getWards().isEmpty() || hotelDTO.getWards() == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject(HttpStatus.BAD_REQUEST,"Phường/xã hotel không được để trống!",null)
            );
        }else if(hotelDTO.getVillage().isEmpty() || hotelDTO.getVillage() == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject(HttpStatus.BAD_REQUEST,"Thôn/xóm hotel không được để trống!",null)
            );
        }else if(hotelDTO.getTotalNumberRoom() == null || hotelDTO.getTotalNumberRoom() <= 0){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject(HttpStatus.BAD_REQUEST,"Tổng số phòng hotel không được để trống hoặc nhỏ hơn 0!",null)
            );
        }else{
            Hotel hotel = hotelService.createHotel(hotelDTO);
            if(hotel != null){
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(HttpStatus.OK,"Tạo mới cơ sở hotel thành công!",hotel)
                );
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject(HttpStatus.BAD_REQUEST,"Tạo mới cơ sở hotel không thành công!",hotel)
            );
        }


    }

}
