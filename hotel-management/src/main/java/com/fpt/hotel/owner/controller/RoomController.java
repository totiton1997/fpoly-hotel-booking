package com.fpt.hotel.owner.controller;

import com.fpt.hotel.model.Room;
import com.fpt.hotel.owner.dto.RoomDTO;
import com.fpt.hotel.owner.service.IRoomService;
import com.fpt.hotel.payload.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owner/rooms")
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin("*")
public class RoomController {

    @Autowired
    IRoomService roomService;

    @GetMapping
    public ResponseEntity<ResponseObject> findAllRoom() {
        List<RoomDTO> roomDTOList = roomService.findAll();
        if (roomDTOList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                    new ResponseObject(HttpStatus.NO_CONTENT.toString(), "Không có phòng nào!", roomDTOList)
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(HttpStatus.OK.toString(), "Hiển thi tất cả các phòng!", roomDTOList)
        );
    }

    @PostMapping
    public ResponseEntity<ResponseObject> create(@RequestBody Room room) {
        RoomDTO roomDTO = roomService.save(room);
        if (roomDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Đã có tên số phòng này!", roomDTO)
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(HttpStatus.OK.toString(), "Thêm phòng thành công!", roomDTO)
        );
    }
}