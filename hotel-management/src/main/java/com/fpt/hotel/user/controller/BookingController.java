package com.fpt.hotel.user.controller;

import com.fpt.hotel.model.Booking;
import com.fpt.hotel.payload.response.ResponseObject;
import com.fpt.hotel.user.dto.request.BookingRequest;
import com.fpt.hotel.user.dto.response.BookingResponse;
import com.fpt.hotel.user.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/user/booking")
@PreAuthorize("isAuthenticated()")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody BookingRequest data) {

        BookingResponse booking = bookingService.create(data);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Đặt phòng thành công", booking));
    }


}
