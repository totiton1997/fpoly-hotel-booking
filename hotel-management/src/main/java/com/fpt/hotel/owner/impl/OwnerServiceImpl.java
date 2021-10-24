package com.fpt.hotel.owner.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpt.hotel.model.User;
import com.fpt.hotel.owner.dto.OwnerDTO;
import com.fpt.hotel.owner.service.OwnerService;
import com.fpt.hotel.repository.UserRepository;
import com.fpt.hotel.service.FileManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FileManagerService fileService;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<OwnerDTO> findAll(String roleName) {
        List<User> users = userRepository.findAll(roleName);
        return users.stream().map(user -> modelMapper.map(user, OwnerDTO.class)).collect(Collectors.toList());
    }

    @Override
    public User save(String folder, String user, MultipartFile[] files) {
        User userJson = new User();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            userJson = objectMapper.readValue(user, User.class);

            Boolean existsByUsername = userRepository.existsByUsername(userJson.getUsername());
            Boolean existsByEmail = userRepository.existsByEmail(userJson.getEmail());

            if (existsByUsername || existsByEmail) {
                return null;
            }

            List<String> fileLists = fileService.save(folder, files);
            String fileName = fileLists.get(0);

            userJson.setImage(fileName);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return userRepository.save(userJson);

    }

    @Override
    public User update(Integer id, User user) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return null;
        }

        return userRepository.save(user);
    }

}
