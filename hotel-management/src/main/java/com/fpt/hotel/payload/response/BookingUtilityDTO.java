package com.fpt.hotel.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookingUtilityDTO {
    private Long id;

    private String description;

    private Long idBooking;

    private Long idUtility;
}
