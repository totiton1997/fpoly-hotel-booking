package com.fpt.hotel.owner.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.hotel.model.User;
import com.fpt.hotel.payload.response.ResponseObject;
import com.fpt.hotel.repository.UserRepository;

@RestController
@RequestMapping("/api/owner")
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin("*")
public class OwnerController {

	@Autowired
	UserRepository userRepository;

	@GetMapping("user")
	public ResponseEntity<?> getAll(@RequestParam("role_name") String role_name) {
		
		List<User> findAll = userRepository.findAll(role_name);
		if (findAll.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(
					new ResponseObject("ok","Không có user nào",null));
		}

		return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject("ok","Trả về dữ liệu user thành công",findAll));
	}

	@PostMapping("user")
	public ResponseEntity<?> create(@RequestBody User data) {

		Boolean existsByUsername = userRepository.existsByUsername(data.getUsername());
		if (existsByUsername) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ResponseObject("Bad Request","Thêm thất bại , đã có username này!!",data));
		}
		User save = userRepository.save(data);
		return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject("Ok","Thêm user thành công",save));
	}
	
	@PutMapping("user/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Integer id ,  @RequestBody User data) {
		
		Optional<User> userOpt = userRepository.findById(id);
		if(userOpt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
					new ResponseObject("No content","Không có user này",null));
		}
		
		User save = userRepository.save(data);
		return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject("Ok","cập nhật user thành công",save));
	}

}
