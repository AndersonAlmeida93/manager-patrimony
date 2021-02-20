package br.com.almeida.managerpatrimony.service;

import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.almeida.managerpatrimony.model.request.UserRequest;
import br.com.almeida.managerpatrimony.model.response.UserResponse;
import br.com.almeida.managerpatrimony.persistence.entity.Users;
import br.com.almeida.managerpatrimony.persistence.repository.UserRepository;
import br.com.almeida.managerpatrimony.service.mapper.Mapper;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Mapper<UserRequest, Users> userRequestMapper;

	@Autowired
	private Mapper<Users, UserResponse> userResponseMapper;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	@Transactional
	public UserResponse create(UserRequest request) {

		Optional<Users> byEmail = userRepository.findByEmail(request.getEmail());

		isTrue(!byEmail.isPresent(), "User already exists");

		Users users = this.userRequestMapper.map(request);

		users.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));

		return userRepository.save(users).map(this.userResponseMapper::map);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<UserResponse> getAll(Pageable page) {

		return userRepository.findAll(page).map(this.userResponseMapper::map);
	}

	@Override
	@Transactional
	public Optional<UserResponse> update(Long id, UserRequest request) {

		Users users = this.userRequestMapper.map(request);

		notNull(users, "user must not be null");

		return userRepository.findById(id).map(it -> {
			it.setName(users.getName());
			it.setEmail(users.getEmail());
			it.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));

			return this.userResponseMapper.map(userRepository.save(it));
		});
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<UserResponse> getId(Long id) {

		return userRepository.findById(id).map(this.userResponseMapper::map);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<UserResponse> getName(String name) {

		return userRepository.findByName(name).map(this.userResponseMapper::map);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<UserResponse> getEmail(String email) {

		return userRepository.findByEmail(email).map(this.userResponseMapper::map);
	}

	@Override
	@Transactional
	public boolean delete(Long id) {

		try {
			userRepository.deleteById(id);
			return true;
		} catch (Exception ex) {
			LOGGER.warn("Error on delete User with id {}", id, ex);
		}
		return false;
	}

}
