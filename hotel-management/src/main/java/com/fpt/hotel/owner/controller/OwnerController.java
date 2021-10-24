package com.fpt.hotel.owner.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpt.hotel.model.User;
import com.fpt.hotel.payload.response.ResponseObject;
import com.fpt.hotel.repository.UserRepository;
import com.fpt.hotel.service.FileManagerService;

@RestController
@RequestMapping("/api/owner")
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin("*")
public class OwnerController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	FileManagerService fileService;

	@GetMapping("user")
	public ResponseEntity<?> getAll(@RequestParam("role_name") String role_name) {

		List<User> findAll = userRepository.findAll(role_name);
		if (findAll.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Không có user nào", null));
		}

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseObject("ok", "Trả về dữ liệu user thành công", findAll));
	}

	@PostMapping(value = "user/{folder}", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> create(@PathVariable("folder") String folder, @RequestPart("user") String user,
			@RequestPart("file") MultipartFile[] files) throws JsonMappingException, JsonProcessingException {

		User userJson = new User();

		ObjectMapper objectMapper = new ObjectMapper();
		userJson = objectMapper.readValue(user, User.class);
		
		Boolean existsByUsername = userRepository.existsByUsername(userJson.getUsername());
		if (existsByUsername) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Thêm thất bại , đã có username này!!", userJson));
		}

		List<String> fileLists = fileService.save(folder, files);
		String fileName = fileLists.get(0);

		userJson.setImage(fileName);

		User save = userRepository.save(userJson);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Ok", "Thêm user thành công", save));
	}

	@PutMapping("user/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody User data) {

		Optional<User> userOpt = userRepository.findById(id);
		if (userOpt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT)
					.body(new ResponseObject("No content", "Không có user này", null));
		}

		User save = userRepository.save(data);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Ok", "cập nhật user thành công", save));
	}

}
