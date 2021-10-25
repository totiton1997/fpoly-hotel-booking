package com.fpt.hotel.user.impl;

import com.fpt.hotel.model.Booking;
import com.fpt.hotel.model.Booking_checkin_checkout;
import com.fpt.hotel.model.Type_room;
import com.fpt.hotel.repository.BookingRepository;
import com.fpt.hotel.repository.Booking_checkin_checkoutRepository;
import com.fpt.hotel.repository.TransactionInfoRepository;
import com.fpt.hotel.repository.TypeRoomRepository;
import com.fpt.hotel.user.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    TransactionInfoRepository transactionInfoRepository;

    @Autowired
    Booking_checkin_checkoutRepository bookingCheckinCheckoutRepository;

    @Autowired
    TypeRoomRepository typeRoomRepository;

    @Override
    public Booking create(Booking booking) {
        booking.setStatus("Đang sử dụng");

        List<Booking_checkin_checkout> checkinCheckouts = booking.getId_checkin_checkout();
        double tongGiaTien = 0;
        for (Booking_checkin_checkout checkin_checkout : checkinCheckouts) {
            Type_room typeRoom = typeRoomRepository.findById(checkin_checkout.getTypeRoom().getId()).get();
            Integer tongNgay = checkin_checkout.getDate_out().getDate() - checkin_checkout.getDate_in().getDate();

            double tongTien = typeRoom.getPrice() * tongNgay;
            tongGiaTien += tongTien;

        }
        booking.setTotalPrice(tongGiaTien);

        Booking newBooking = bookingRepository.save(booking);

        bookingCheckinCheckoutRepository.saveAll(checkinCheckouts);

        return newBooking;
    }
}
