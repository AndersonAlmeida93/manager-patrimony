package br.com.almeida.managerpatrimony.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.almeida.managerpatrimony.model.request.PatrimonyRequest;
import br.com.almeida.managerpatrimony.persistence.entity.Brand;
import br.com.almeida.managerpatrimony.persistence.entity.Patrimony;
import br.com.almeida.managerpatrimony.persistence.repository.BrandRepository;

@Component
public class PatrimonyRequestMapper implements Mapper<PatrimonyRequest, Patrimony>{
	
	@Autowired
	private BrandRepository repository;

	@Override
	public Patrimony map(PatrimonyRequest input) {
		if(input == null) {
			return null;
		}
		
		Brand brand = input.getBrandId() == null ? null : repository.findById(input.getBrandId()).orElseThrow(() -> new IllegalArgumentException("Invalid brand id"));
		
		Patrimony result = new Patrimony();
		
		result.setName(input.getName());
		result.setDescription(input.getDescription());
		result.setBrand(brand);
		
		return result;
	}

}
