package com.fpt.hotel.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UtilityDTO {
    private Long id;

    private String utilityName;

    private String image;

    private List<DetaiUtilityDTO> detailUtilities;

    private TypeUtilityDTO typeUtility;

}
