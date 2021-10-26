package com.fpt.hotel.user.service;

import com.fpt.hotel.model.Booking;
import com.fpt.hotel.user.dto.BookingResponse;


public interface BookingService {

    BookingResponse create(Booking booking);
}
