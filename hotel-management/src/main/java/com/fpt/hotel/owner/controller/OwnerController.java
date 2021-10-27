package com.fpt.hotel.owner.controller;

import com.fpt.hotel.model.User;
import com.fpt.hotel.owner.dto.OwnerDTO;
import com.fpt.hotel.owner.service.OwnerService;
import com.fpt.hotel.payload.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/owner")
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin("*")
public class OwnerController {

    @Autowired
    OwnerService ownerService;

    @GetMapping("user")
    public ResponseEntity<ResponseObject> getAll(@RequestParam("role_name") String roleName) {

        List<OwnerDTO> findAll = ownerService.findAll(roleName);
        if (findAll.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Không có user nào", null));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Trả về dữ liệu user thành công", findAll));
    }

    @PostMapping(value = "user/{folder}", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseObject> create(@PathVariable("folder") String folder, @RequestPart("user") String user,
                                    @RequestPart(name = "file" , required = false) List<MultipartFile>  files) {

        User userRes = ownerService.save(folder, user, files);

        if (userRes == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Thêm thất bại , đã có username hoặc email này!!", userRes));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Ok", "Thêm user thành công", userRes));
    }

    @PutMapping("user/{id}")
    public ResponseEntity<ResponseObject> update(@PathVariable("id") Integer id, @RequestBody User user) {

        User update = ownerService.update(id, user);

        if (update == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("Not found", "Không có user này", null));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Ok", "cập nhật user thành công", update));
    }

}
