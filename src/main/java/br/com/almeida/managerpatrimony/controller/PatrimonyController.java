package br.com.almeida.managerpatrimony.controller;

import java.util.List;
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

import br.com.almeida.managerpatrimony.model.request.PatrimonyRequest;
import br.com.almeida.managerpatrimony.model.response.PatrimonyReponse;
import br.com.almeida.managerpatrimony.service.PatrimonyService;

@RestController
@RequestMapping("/v1/patrimony")
public class PatrimonyController {

	@Autowired
	private PatrimonyService service;

	@PostMapping
	public ResponseEntity<PatrimonyReponse> create(@RequestBody @Valid PatrimonyRequest request) {

		return ResponseEntity.ok(service.create(request));
	}

	@GetMapping
	public ResponseEntity<Page<PatrimonyReponse>> getAll(Pageable page) {

		return ResponseEntity.ok(service.getAll(page));
	}

	@PutMapping("/{id}")
	public ResponseEntity<PatrimonyReponse> update(@PathVariable("id") Long id,
			@RequestBody @Valid PatrimonyRequest request) {

		Optional<PatrimonyReponse> update = service.update(id, request);

		if (!update.isPresent()) {
			return ResponseEntity.badRequest().build();
		}

		return ResponseEntity.ok(update.get());
	}

	@GetMapping("/{id}")
	public ResponseEntity<PatrimonyReponse> getById(@PathVariable("id") Long id) {

		Optional<PatrimonyReponse> optional = service.getId(id);

		if (!optional.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(optional.get());
	}

	@GetMapping("/{name}/name")
	public ResponseEntity<PatrimonyReponse> getByName(@PathVariable("name") String name) {

		Optional<PatrimonyReponse> optional = service.getName(name);

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

	@GetMapping("/{brandId}/patrimonys")
	public ResponseEntity<List<PatrimonyReponse>> patrimonys(@PathVariable("brandId") Long brandId) {

		return ResponseEntity.ok(service.getAllByBrandId(brandId));
	}

	@GetMapping("/{numberFall}/patrimony")
	public ResponseEntity<PatrimonyReponse> patrimonyByNumberFall(@PathVariable("numberFall") Long numberFall) {

		Optional<PatrimonyReponse> optional = service.getByNumberFall(numberFall);
		if (!optional.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(optional.get());
	}
}
