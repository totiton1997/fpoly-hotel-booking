package com.fpt.hotel.user.impl;

import com.fpt.hotel.model.Booking;
import com.fpt.hotel.model.Booking_checkin_checkout;
import com.fpt.hotel.model.Type_room;
import com.fpt.hotel.repository.BookingRepository;
import com.fpt.hotel.repository.Booking_checkin_checkoutRepository;
import com.fpt.hotel.repository.TransactionInfoRepository;
import com.fpt.hotel.repository.TypeRoomRepository;
import com.fpt.hotel.user.dto.request.BookingRequest;
import com.fpt.hotel.user.dto.request.CheckInCheckOutRequest;
import com.fpt.hotel.user.dto.response.BookingResponse;
import com.fpt.hotel.user.service.BookingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public BookingResponse create(BookingRequest bookingRequest) {
        bookingRequest.setStatus("Đang sử dụng");

        List<CheckInCheckOutRequest> checkinCheckouts = bookingRequest.getCheckinCheckouts();
        double tongGiaTien = 0;
        Type_room typeRoom = null;

        for (CheckInCheckOutRequest checkinCheckout : checkinCheckouts) {

            typeRoom = typeRoomRepository.findById(checkinCheckout.getIdTypeRoom()).get();
            int ngayDi = checkinCheckout.getDate_out().toLocalDate().getDayOfMonth();
            int ngayDen = checkinCheckout.getDate_in().toLocalDate().getDayOfMonth();

            Integer tongNgay = ngayDi - ngayDen;

            double tongTien = typeRoom.getPrice() * tongNgay;
            tongGiaTien += tongTien;

        }
        bookingRequest.setTotalPrice(tongGiaTien);

        Booking booking = modelMapper.map(bookingRequest , Booking.class);

        Booking newBooking = bookingRepository.save(booking);

        for (CheckInCheckOutRequest checkinCheckout : checkinCheckouts) {
            checkinCheckout.setIdBooking(newBooking.getId());
        }

        List<Booking_checkin_checkout> booking_checkin_checkouts =  checkinCheckouts.stream().map(checkinCheckout -> modelMapper.map(
                checkinCheckout , Booking_checkin_checkout.class)).collect(Collectors.toList());

        newBooking.setCheckinCheckouts(booking_checkin_checkouts);

        bookingCheckinCheckoutRepository.saveAll(booking_checkin_checkouts);

        return getBookingResponse(newBooking);

    }

    private BookingResponse getBookingResponse(Booking newBooking) {
        BookingResponse bookingResponse = new BookingResponse();

        modelMapper.map(newBooking, bookingResponse);
        return bookingResponse;
    }
}
