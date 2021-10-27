package com.fpt.hotel.owner.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomDTO {
    private Long id;

    private int numberRoom;

    private String status;

    private Boolean enabled;

    private String description;

    private Long idTypeRoom;

    private Long idHotel;
}
