package br.com.almeida.managerpatrimony.service.mapper;

import org.springframework.stereotype.Component;

import br.com.almeida.managerpatrimony.model.request.BrandRequest;
import br.com.almeida.managerpatrimony.persistence.entity.Brand;

@Component
public class BrandRequestMapper implements Mapper<BrandRequest, Brand>{

	@Override
	public Brand map(BrandRequest input) {
		
		if( input == null) {
			return null;
		}
		
		Brand result = new Brand();
		result.setName(input.getName());
		
		return result;
	}

	
}
