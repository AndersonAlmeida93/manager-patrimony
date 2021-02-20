package br.com.almeida.managerpatrimony.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import br.com.almeida.managerpatrimony.model.request.BrandRequest;
import br.com.almeida.managerpatrimony.model.response.BrandResponse;

public interface BrandService {

	BrandResponse create (BrandRequest request);
	
	Page<BrandResponse> getAll(Pageable page);
	
	Optional<BrandResponse> update (Long id, BrandRequest request);
	
	Optional<BrandResponse> getId (Long id);
	
	Optional<BrandResponse> getName (String name);
	
	boolean delete (Long id);
}
