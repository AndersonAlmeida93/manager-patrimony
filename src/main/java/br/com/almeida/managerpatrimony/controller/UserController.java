package br.com.almeida.managerpatrimony.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.almeida.managerpatrimony.model.request.UserRequest;
import br.com.almeida.managerpatrimony.model.response.UserResponse;
import br.com.almeida.managerpatrimony.service.UserService;

@RestController
@RequestMapping("/v1/users")
public class UserController {

	@Autowired
	private UserService service;

	@PostMapping
	public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest request) {

		return ResponseEntity.ok(service.create(request));
	}

	@GetMapping
	public ResponseEntity<Page<UserResponse>> getAll(Pageable page) {

		return ResponseEntity.ok(service.getAll(page));
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserResponse> update(@PathVariable("id") Long id, @RequestBody @Valid UserRequest request) {

		Optional<UserResponse> optional = service.update(id, request);

		if (!optional.isPresent()) {
			return ResponseEntity.badRequest().build();
		}

		return ResponseEntity.ok(optional.get());
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getById(@PathVariable("id") Long id) {

		Optional<UserResponse> optional = service.getId(id);

		if (!optional.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(optional.get());
	}

	@GetMapping("/{name}/name")
	public ResponseEntity<UserResponse> getByName(@PathVariable("name") String name) {

		Optional<UserResponse> optional = service.getName(name);

		if (!optional.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(optional.get());
	}

	@GetMapping("/{email}/email")
	public ResponseEntity<UserResponse> getByEmail(@PathVariable("email") String email) {

		Optional<UserResponse> optional = service.getEmail(email);

		if (!optional.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(optional.get());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {

		if (service.delete(id)) {
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.badRequest().build();
	}
}
