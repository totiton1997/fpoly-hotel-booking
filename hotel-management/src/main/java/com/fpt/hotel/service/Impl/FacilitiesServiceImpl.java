package com.fpt.hotel.service.Impl;

import com.fpt.hotel.model.Facility;
import com.fpt.hotel.repository.FacilitiesRepository;
import com.fpt.hotel.service.IFacilitiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class FacilitiesServiceImpl implements IFacilitiesService {

    Logger logger = LoggerFactory.getLogger(FacilitiesServiceImpl.class);

    @Autowired
    private FacilitiesRepository facilitiesRepository;

    @Override
    public List<Facility> findAllFacilities() {

        logger.info("Find all data: "+facilitiesRepository.findAll());
        return facilitiesRepository.findAll();
    }
}
