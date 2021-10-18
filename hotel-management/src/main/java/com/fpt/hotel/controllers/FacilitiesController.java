package com.fpt.hotel.controllers;


import com.fpt.hotel.payload.response.ResponseObject;
import com.fpt.hotel.service.Impl.FacilitiesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/facility")
public class FacilitiesController {

    @Autowired
    private FacilitiesServiceImpl facilitiesServices;

    @GetMapping("/findall")
    public ResponseEntity<?> findAllFacilities(){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok","Find all facilities successfuly!",facilitiesServices.findAllFacilities())
        );
    }

}
