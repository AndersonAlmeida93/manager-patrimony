package br.com.almeida.managerpatrimony.service.mapper;

import org.springframework.stereotype.Component;

import br.com.almeida.managerpatrimony.model.response.PatrimonyReponse;
import br.com.almeida.managerpatrimony.persistence.entity.Patrimony;

@Component
public class PatrimonyResponseMapper implements Mapper<Patrimony, PatrimonyReponse> {

	@Override
	public PatrimonyReponse map(Patrimony input) {
		if (input == null) {
			return null;
		}

		PatrimonyReponse response = new PatrimonyReponse();
		response.setId(input.getId());
		response.setName(input.getName());
		response.setDescription(input.getDescription());
		response.setNumberFall(input.getNumberFall());

		if (input.getBrand() != null) {
			response.setBrandId(input.getBrand().getId());
		}

		return response;
	}

}
