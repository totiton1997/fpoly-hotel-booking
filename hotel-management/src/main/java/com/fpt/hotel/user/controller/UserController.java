package com.fpt.hotel.user.controller;

import com.fpt.hotel.model.Booking;
import com.fpt.hotel.model.User;
import com.fpt.hotel.payload.response.ResponseObject;
import com.fpt.hotel.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("isAuthenticated()")
public class UserController {

    @Autowired
    BookingRepository bookingRepository;


    @GetMapping("{id}")
    public ResponseEntity<?> getAll(@PathVariable ( "id") Long id) {

        List<Booking> findAll = bookingRepository.findById_user(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Trả về dữ liệu danh sách đặt phòng thành công", findAll));
    }

}
