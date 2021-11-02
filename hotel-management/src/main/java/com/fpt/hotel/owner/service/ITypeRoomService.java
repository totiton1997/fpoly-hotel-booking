package com.fpt.hotel.owner.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fpt.hotel.model.Type_room;
import com.fpt.hotel.owner.dto.response.TypeRoomResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ITypeRoomService {

    Type_room save(String folder, String typeRoom, List<MultipartFile> files) throws JsonProcessingException;

    List<TypeRoomResponse> findAll();
}
