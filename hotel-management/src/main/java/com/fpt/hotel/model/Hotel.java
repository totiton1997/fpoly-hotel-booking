package com.fpt.hotel.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1, max = 50, message = "Base name exceeds 50 characters")
    private String name;
    private String city;
    private String district;
    private String wards;
    private String village;
    private Integer totalNumberRoom;
    private String images;

    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms;

}
