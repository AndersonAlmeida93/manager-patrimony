package br.com.almeida.managerpatrimony.service.mapper;

import org.springframework.stereotype.Component;

import br.com.almeida.managerpatrimony.model.response.BrandResponse;
import br.com.almeida.managerpatrimony.persistence.entity.Brand;

@Component
public class BrandResponseMapper implements Mapper<Brand, BrandResponse> {

	@Override
	public BrandResponse map(Brand input) {
		if (input == null) {
			return null;
		}

		BrandResponse response = new BrandResponse();
		response.setId(input.getId());
		response.setName(input.getName());

		return response;
	}

}
