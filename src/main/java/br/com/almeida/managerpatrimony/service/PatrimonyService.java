package br.com.almeida.managerpatrimony.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.almeida.managerpatrimony.model.request.PatrimonyRequest;
import br.com.almeida.managerpatrimony.model.response.PatrimonyReponse;

public interface PatrimonyService {

	PatrimonyReponse create (PatrimonyRequest request);
	
	Page<PatrimonyReponse> getAll (Pageable page);
	
	Optional<PatrimonyReponse> update (Long id, PatrimonyRequest request);
	
	Optional<PatrimonyReponse> getId (Long id);
	
	Optional<PatrimonyReponse> getName (String name);
	
	boolean delete (Long id);
	
	List<PatrimonyReponse> getAllByBrandId (Long brandId);
	
	Optional<PatrimonyReponse> getByNumberFall(Long numberFall);
	
}
