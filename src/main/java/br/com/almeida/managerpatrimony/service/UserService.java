package br.com.almeida.managerpatrimony.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.almeida.managerpatrimony.model.request.UserRequest;
import br.com.almeida.managerpatrimony.model.response.UserResponse;

public interface UserService {

	UserResponse create (UserRequest request);
	
	Page<UserResponse> getAll (Pageable page);
	
	Optional<UserResponse> update (Long id, UserRequest request);
	
	Optional<UserResponse> getId (Long id);
	
	Optional<UserResponse> getName (String name);
	
	Optional<UserResponse> getEmail (String email);
	
	boolean delete (Long id);
}
