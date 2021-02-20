package br.com.almeida.managerpatrimony.service.mapper;

import org.springframework.stereotype.Component;

import br.com.almeida.managerpatrimony.model.response.UserResponse;
import br.com.almeida.managerpatrimony.persistence.entity.Users;

@Component
public class UserResponseMapper implements Mapper<Users, UserResponse> {

	@Override
	public UserResponse map(Users input) {
		if (input == null) {
			return null;
		}

		UserResponse response = new UserResponse();
		response.setName(input.getName());
		response.setEmail(input.getEmail());
		response.setId(input.getId());

		return response;
	}

}
