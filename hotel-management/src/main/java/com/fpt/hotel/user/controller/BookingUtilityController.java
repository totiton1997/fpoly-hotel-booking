package com.fpt.hotel.user.controller;

import com.fpt.hotel.model.*;
import com.fpt.hotel.payload.response.BookingUtilityDTO;
import com.fpt.hotel.payload.response.ResponseObject;
import com.fpt.hotel.repository.BookingRepository;
import com.fpt.hotel.repository.BookingUtilityRepository;
import com.fpt.hotel.repository.TransactionInfoRepository;
import com.fpt.hotel.repository.UtilityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user/booking-utilities")
@PreAuthorize("isAuthenticated()")
public class BookingUtilityController {

    @Autowired
    BookingUtilityRepository bookingUtilityRepository;

    @Autowired
    UtilityRepository utilityRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    TransactionInfoRepository transactionInfoRepository;

    @GetMapping("")
    public ResponseEntity<ResponseObject> getAll() {
        List<BookingUtilityDTO> utilityDTOS = bookingUtilityRepository.findAll().stream().map(
                bookingUtility -> modelMapper.map(bookingUtility, BookingUtilityDTO.class)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Trả về dữ liệu tiện ích thành công", utilityDTOS));
    }

    @PostMapping
    public ResponseEntity<ResponseObject> createBookingUtility(@RequestBody BookingUtility bookingUtility) {

        Long idBooking = bookingUtility.getBooking().getId();

        Booking booking = bookingRepository.findById(idBooking).get();
        Utility utility = utilityRepository.findById(bookingUtility.getUtility().getId()).get();
        Transaction_Info transaction_info = transactionInfoRepository.findByIdBooking(idBooking);

        bookingUtility.setBooking(booking);
        bookingUtility.setUtility(utility);

        Double tongTien = transaction_info.getTotal_price();
        List<DetailUtility> detailUtilities = utility.getDetailUtilities();
        for (DetailUtility detailUtility : detailUtilities) {
            double tongTienDichVu = detailUtility.getPrice();
            tongTien += tongTienDichVu;
        }

        transaction_info.setTotal_price(tongTien);

        transactionInfoRepository.save(transaction_info);
        BookingUtility newBookingUtility = bookingUtilityRepository.save(bookingUtility);

        BookingUtilityDTO bookingUtilityDTO = modelMapper.map(newBookingUtility, BookingUtilityDTO.class);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Đặt dịch vụ phòng thành công", bookingUtilityDTO));
    }
}
