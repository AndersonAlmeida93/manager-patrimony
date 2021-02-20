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

import br.com.almeida.managerpatrimony.model.request.BrandRequest;
import br.com.almeida.managerpatrimony.model.response.BrandResponse;
import br.com.almeida.managerpatrimony.service.BrandService;

@RestController
@RequestMapping(value = "/v1/brand")
public class BrandController {

	@Autowired
	private BrandService service;

	@PostMapping
	public ResponseEntity<BrandResponse> create(@RequestBody @Valid BrandRequest request) {

		return ResponseEntity.ok(service.create(request));
	}

	@GetMapping
	public ResponseEntity<Page<BrandResponse>> getAll(Pageable page) {

		return ResponseEntity.ok(service.getAll(page));
	}

	@PutMapping("/{id}")
	public ResponseEntity<BrandResponse> update(@PathVariable("id") Long id, @RequestBody @Valid BrandRequest request) {

		Optional<BrandResponse> update = service.update(id, request);

		if (!update.isPresent()) {
			return ResponseEntity.badRequest().build();
		}

		return ResponseEntity.ok(update.get());
	}

	@GetMapping("/{id}")
	public ResponseEntity<BrandResponse> getById(@PathVariable("id") Long id) {

		Optional<BrandResponse> optional = service.getId(id);

		if (!optional.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(optional.get());
	}

	@GetMapping("/{name}/name")
	public ResponseEntity<BrandResponse> getName(@PathVariable("name") String name) {

		Optional<BrandResponse> optional = service.getName(name);

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
