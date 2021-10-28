package com.fpt.hotel.owner.service;

import com.fpt.hotel.model.User;
import com.fpt.hotel.owner.dto.response.OwnerResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OwnerService {

    List<OwnerResponse> findAll(String roleName);

    User save(String folder, String user, List<MultipartFile>  files);

    User update(Integer id, User user);
}
