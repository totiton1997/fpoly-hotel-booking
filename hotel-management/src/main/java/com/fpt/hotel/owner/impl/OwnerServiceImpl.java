package com.fpt.hotel.owner.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpt.hotel.model.ERole;
import com.fpt.hotel.model.Hotel;
import com.fpt.hotel.model.Role;
import com.fpt.hotel.model.User;
import com.fpt.hotel.owner.dto.request.UserRequest;
import com.fpt.hotel.owner.dto.response.OwnerResponse;
import com.fpt.hotel.owner.service.OwnerService;
import com.fpt.hotel.repository.HotelRepository;
import com.fpt.hotel.repository.RoleRepository;
import com.fpt.hotel.repository.UserRepository;
import com.fpt.hotel.service.FileManagerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FileManagerService fileService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public List<OwnerResponse> findAll(String roleName) {
        List<User> users = userRepository.findAll(roleName);
        return users.stream().map(user -> modelMapper.map(user, OwnerResponse.class)).collect(Collectors.toList());
    }

    @Override
    public User save(String folder, String user, List<MultipartFile> files) {
        UserRequest userRequest = new UserRequest();
        User userJson = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            userRequest = objectMapper.readValue(user, UserRequest.class);

            Hotel hotel = hotelRepository.findById(userRequest.getId_hotel()).get();

            userJson = modelMapper.map(userRequest, User.class);
            userJson.setHotel(hotel);
            userJson.setPassword(encoder.encode(userRequest.getPassword()));

            Set<Role> roles = new HashSet<>();
            Role userRole = roleRepository.findByName(ERole.ROLE_STAFF)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);

            // set đối tượng vừa thêm là nhân viên
            userJson.setRoles(roles);

            Boolean existsByUsername = userRepository.existsByUsername(userRequest.getUsername());
            Boolean existsByEmail = userRepository.existsByEmail(userRequest.getEmail());

            if (existsByUsername || existsByEmail) {
                return null;
            }
            String fileName = "";
            if (files != null) {
                List<String> fileLists = fileService.save(folder, files);
                fileName = fileLists.get(0);
            }

            userJson.setImage(fileName);


        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return userRepository.save(userJson);
    }

    @Override
    public OwnerResponse update(Integer id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return null;
        }
        User user = userOpt.get();
        user.setEnabled(user.getEnabled() == 1 ? 0 : 1);

        return modelMapper.map(userRepository.save(user),OwnerResponse.class);
    }

    @Override
    public OwnerResponse updateHotel(Integer idUser, Long idHotel) {
        Optional<User> userOptional = userRepository.findById(idUser);
        Optional<Hotel> hotelOptional = hotelRepository.findById(idHotel);
        OwnerResponse ownerResponse = null;
        if(userOptional.isPresent() && hotelOptional.isPresent()){
            User user = userOptional.get();
            Hotel hotel = hotelOptional.get();
            user.setHotel(hotel);
            User newUser = userRepository.save(user);
            ownerResponse = modelMapper.map(newUser , OwnerResponse.class );
        }
        return ownerResponse;
    }

}
