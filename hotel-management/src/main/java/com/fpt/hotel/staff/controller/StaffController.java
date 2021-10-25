package com.fpt.hotel.staff.controller;

import com.fpt.hotel.model.Booking;
import com.fpt.hotel.model.Transaction_Info;
import com.fpt.hotel.repository.BookingRepository;
import com.fpt.hotel.repository.TransactionInfoRepository;
import com.fpt.hotel.repository.UserRepository;
import com.fpt.hotel.staff.dto.TransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("hasRole('STAFF')")
public class StaffController {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionInfoRepository transactionInfoRepository;


    @PostMapping("api/confirm-booking")
    public ResponseEntity<?> confirmBooking(@RequestBody TransactionRequest transactionRequest) {
        List<Booking> bookings = bookingRepository.listBookingByIdUser(transactionRequest.getId_user(), transactionRequest.getId_hotel());
        Booking booking = bookings.get(0);
        booking.setStatus("Đã checkin");
        Booking newBooking = bookingRepository.save(booking);

        if (newBooking.getStatus() == "Đã checkin") {
            Transaction_Info transactionInfoResp = new Transaction_Info();
            Transaction_Info transaction_info = new Transaction_Info();
            transaction_info.setId_booking(newBooking);
            transaction_info.setId_creator(userRepository.findById(transactionRequest.getId_creator()).get());
            transaction_info.setStatus("Đã checkin");
            transactionInfoResp = transactionInfoRepository.save(transaction_info);
            return ResponseEntity.ok().body(transactionInfoResp);

        }

        return ResponseEntity.ok().body(null);
    }

}
