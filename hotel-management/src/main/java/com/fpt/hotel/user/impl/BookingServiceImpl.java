package com.fpt.hotel.user.impl;

import com.fpt.hotel.model.Booking;
import com.fpt.hotel.model.Booking_checkin_checkout;
import com.fpt.hotel.model.Type_room;
import com.fpt.hotel.repository.BookingRepository;
import com.fpt.hotel.repository.Booking_checkin_checkoutRepository;
import com.fpt.hotel.repository.TransactionInfoRepository;
import com.fpt.hotel.repository.TypeRoomRepository;
import com.fpt.hotel.user.dto.BookingResponse;
import com.fpt.hotel.user.service.BookingService;
import org.modelmapper.ModelMapper;
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

    @Autowired
    ModelMapper modelMapper;

    @Override
    public BookingResponse create(Booking booking) {
        booking.setStatus("Đang sử dụng");

        List<Booking_checkin_checkout> checkinCheckouts = booking.getCheckinCheckouts();
        double tongGiaTien = 0;

        for (Booking_checkin_checkout checkinCheckout : checkinCheckouts) {

            Type_room typeRoom = typeRoomRepository.findById(checkinCheckout.getTypeRoom().getId()).get();
            int ngayDi = checkinCheckout.getDate_out().toLocalDate().getDayOfMonth();
            int ngayDen = checkinCheckout.getDate_in().toLocalDate().getDayOfMonth();

            Integer tongNgay = ngayDi - ngayDen;

            double tongTien = typeRoom.getPrice() * tongNgay;
            tongGiaTien += tongTien;

        }
        booking.setTotalPrice(tongGiaTien);

        Booking newBooking = bookingRepository.save(booking);

        for (Booking_checkin_checkout checkinCheckout : checkinCheckouts) {
            checkinCheckout.setBooking(newBooking);
        }

        bookingCheckinCheckoutRepository.saveAll(checkinCheckouts);

        return getBookingResponse(newBooking);

    }

    private BookingResponse getBookingResponse(Booking newBooking) {
        BookingResponse bookingResponse = new BookingResponse();

        modelMapper.map(newBooking, bookingResponse);
        return bookingResponse;
    }
}
