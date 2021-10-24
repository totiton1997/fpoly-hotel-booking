package com.fpt.hotel.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "rooms")
@Getter
@Setter
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    private int number_room;

    private String status;

    private Boolean enabled;

    private String description;

    @ManyToOne
    @JoinColumn(name = "id_room")
    private Type_room room;

    @ManyToOne
    @JoinColumn(name = "id_hotel")
    private Hotel hotel;
}