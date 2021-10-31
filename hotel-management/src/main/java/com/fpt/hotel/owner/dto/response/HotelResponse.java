package com.fpt.hotel.owner.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HotelResponse {
    private Long id;
    private String name;
    private String city;
    private String address;
    private Integer totalNumberRoom;
    private String images;

    private List<RoomResponse> rooms;

}
