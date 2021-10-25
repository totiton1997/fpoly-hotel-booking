package com.fpt.hotel.owner.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpt.hotel.model.TypeRoomImage;
import com.fpt.hotel.model.Type_room;
import com.fpt.hotel.payload.response.ResponseObject;
import com.fpt.hotel.repository.TypeRoomImageRepository;
import com.fpt.hotel.repository.TypeRoomRepository;
import com.fpt.hotel.service.FileManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/owner")
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin("*")
public class TypeRoomController {

    @Autowired
    TypeRoomRepository typeRoomRepository;

    @Autowired
    TypeRoomImageRepository typeRoomImageRepository;

    @Autowired
    FileManagerService fileManagerService;

    @PostMapping(value = "type_room/{folder}", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseObject> create(@PathVariable("folder") String folder, @RequestPart("typeRoom") String typeRoom,
                                                 @RequestPart("file") MultipartFile[] files) throws JsonProcessingException {


        // convert json về object
        ObjectMapper objectMapper = new ObjectMapper();
        Type_room typeRoomJson = objectMapper.readValue(typeRoom, Type_room.class);

        // lưu thể loại phòng
        Type_room typeRoomSave = typeRoomRepository.save(typeRoomJson);


        // tạo đối tượng TypeRoomImage
        TypeRoomImage typeRoomImage = new TypeRoomImage();
        typeRoomImage.setTypeRoom(typeRoomSave);

        // lưu ảnh vào folder và tách mảng ảnh thành chuỗi
        List<String> listFile = fileManagerService.save(folder, files);
        String imageName = String.join(",", listFile);
        typeRoomImage.setImage(imageName);
        // lưu TypeRoomImage vào database sau đó trả về đối tượng vừa thêm
        TypeRoomImage roomImage = typeRoomImageRepository.save(typeRoomImage);

        // thêm TypeRoomImage vào list để thêm vào loại phòng
        List<TypeRoomImage> typeRoomImages = new ArrayList<>();
        typeRoomImages.add(roomImage);
        typeRoomSave.setTypeRoomImages(typeRoomImages);

        // cập nhật lại loại phòng khi có đủ các trường trong TypeRoomImage
        Type_room typeRoomResp = typeRoomRepository.save(typeRoomSave);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Ok", "Thêm loại phòng thành công", typeRoomResp));
    }
}
