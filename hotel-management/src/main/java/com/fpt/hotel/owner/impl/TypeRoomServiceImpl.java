package com.fpt.hotel.owner.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpt.hotel.model.TypeRoomImage;
import com.fpt.hotel.model.Type_room;
import com.fpt.hotel.owner.service.ITypeRoomService;
import com.fpt.hotel.repository.TypeRoomImageRepository;
import com.fpt.hotel.repository.TypeRoomRepository;
import com.fpt.hotel.service.FileManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypeRoomServiceImpl implements ITypeRoomService {

    @Autowired
    TypeRoomRepository typeRoomRepository;

    @Autowired
    TypeRoomImageRepository typeRoomImageRepository;

    @Autowired
    FileManagerService fileManagerService;

    @Override
    public Type_room save(String folder, String typeRoom, List<MultipartFile> files) throws JsonProcessingException {

        Type_room typeRoomSave = getTypeRoom(typeRoom);

        List<TypeRoomImage> typeRoomImages = getTypeRoomImages(folder, files, typeRoomSave);

        // thêm TypeRoomImage vào list để thêm vào loại phòng

        typeRoomSave.setTypeRoomImages(typeRoomImages);

        // cập nhật lại loại phòng khi có đủ các trường trong TypeRoomImage
        return typeRoomRepository.save(typeRoomSave);

    }

    // lưu typeRoomImage
    private List<TypeRoomImage> getTypeRoomImages(String folder, List<MultipartFile> files, Type_room typeRoomSave) {
        // tạo đối tượng TypeRoomImage
        List<TypeRoomImage> typeRoomImages = new ArrayList<>();
        TypeRoomImage typeRoomImage = null;

        if (files != null) {
            List<String> listFile = fileManagerService.save(folder, files);
            for (String file : listFile) {
                typeRoomImage = new TypeRoomImage();
                typeRoomImage.setTypeRoom(typeRoomSave);
                typeRoomImage.setImage(file);
                typeRoomImages.add(typeRoomImage);
            }
        }

        return typeRoomImageRepository.saveAll(typeRoomImages);

    }

    // convert obj sang TypeRoom và lưu vào db
    private Type_room getTypeRoom(String typeRoom) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Type_room typeRoomJson = objectMapper.readValue(typeRoom, Type_room.class);

        return typeRoomRepository.save(typeRoomJson);
    }
}
