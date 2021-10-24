package com.fpt.hotel.user.controller;

import com.fpt.hotel.model.Booking;
import com.fpt.hotel.model.Booking_checkin_checkout;
import com.fpt.hotel.payload.response.ResponseObject;
import com.fpt.hotel.repository.BookingRepository;
import com.fpt.hotel.repository.Booking_checkin_checkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("api/booking")
@PreAuthorize("isAuthenticated()")
public class BookingController {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    Booking_checkin_checkoutRepository bookingCheckinCheckoutRepository;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody Booking data) {

        data.setStatus("Đang sử dụng");

        Booking booking = bookingRepository.save(data);

        List<Booking_checkin_checkout> checkinCheckouts = booking.getId_checkin_checkout();

        bookingCheckinCheckoutRepository.saveAll(checkinCheckouts);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Đặt phòng thành công", booking));

    }


}
