package com.bryan.api.rest.app.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bryan.api.rest.app.entity.User;
import com.bryan.api.rest.app.repository.UserRepository;
import com.bryan.api.rest.app.service.S3Service;
import com.bryan.api.rest.app.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private S3Service s3Service;

	// Create new User
	@PostMapping
	public User create(@RequestBody User user) throws Exception {

		// return
		// ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));

		user.setFotoUrl(s3Service.getObjectUrl(user.getFotoPath()));
		user.setCedulaUrl(s3Service.getObjectUrl(user.getCedulaPath()));
		if (StringUtils.endsWithIgnoreCase(user.getFotoPath(), "jpg")
				&& StringUtils.endsWithIgnoreCase(user.getCedulaPath(), "pdf")) {

			userService.save(user);
		} else {

			throw new Exception("Error: Solo se permite CEDULA con extension .pdf y FOTO con extension .jpg.!!");
		
		}

		return user;
	}

	// Read an User
	@GetMapping("/{id}")
	public ResponseEntity<?> read(@PathVariable(value = "id") Long userId) {

		Optional<User> oUser = userService.findById(userId);

		if (!oUser.isPresent()) {

			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(oUser);
	}

	// Update an User
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody User userDetails, @PathVariable(value = "id") Long userId) {

		Optional<User> user = userService.findById(userId);

		if (!user.isPresent()) {

			return ResponseEntity.notFound().build();
		}

		// BeanUtils.copyProperties(userDetails, user.get());
		user.get().setNombre(userDetails.getNombre());
		user.get().setClave(userDetails.getClave());
		user.get().setEmail(userDetails.getEmail());
		user.get().setEstado(userDetails.getEstado());

		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user.get()));

	}

	// Delete an User
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long userId) {

		if (!userService.findById(userId).isPresent()) {

			return ResponseEntity.notFound().build();
		}

		userService.deleteById(userId);
		return ResponseEntity.ok().build();
	}

	// Read all Users
	@GetMapping
	public List<User> readAll() {

		List<User> users = StreamSupport.stream(userService.findAll().spliterator(), false)
				.collect(Collectors.toList());

		return userRepository.findAll().stream()
				.peek(user -> user.setFotoUrl(s3Service.getObjectUrl(user.getFotoPath())))
				.peek(user -> user.setCedulaUrl(s3Service.getObjectUrl(user.getCedulaPath())))
				.collect(Collectors.toList());
	}

}
