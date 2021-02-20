package br.com.almeida.managerpatrimony.service.mapper;

import org.springframework.stereotype.Component;

import br.com.almeida.managerpatrimony.model.request.UserRequest;
import br.com.almeida.managerpatrimony.persistence.entity.Users;

@Component
public class UserRequestMapper implements Mapper<UserRequest, Users> {

	@Override
	public Users map(UserRequest input) {
		if (input == null) {
			return null;
		}

		Users result = new Users();
		result.setName(input.getName());
		result.setEmail(input.getEmail());
		result.setPassword(input.getPassword());

		return result;
	}

}
