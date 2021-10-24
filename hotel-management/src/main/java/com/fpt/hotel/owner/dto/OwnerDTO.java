package com.fpt.hotel.owner.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter
@Setter
public class OwnerDTO {

    private Integer id;
    private String first_name;
    private String last_name;
    private String phone;
    private String email;

    @Temporal(TemporalType.DATE)
    private Date date_of_birth;
    private Integer enabled;
    private String image;
    private String username;
    private Integer id_creator;
}