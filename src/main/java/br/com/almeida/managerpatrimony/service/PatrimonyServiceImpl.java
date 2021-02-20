package br.com.almeida.managerpatrimony.service;

import static org.springframework.util.Assert.notNull;
import static org.springframework.util.Assert.state;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.almeida.managerpatrimony.model.request.PatrimonyRequest;
import br.com.almeida.managerpatrimony.model.response.PatrimonyReponse;
import br.com.almeida.managerpatrimony.persistence.entity.Patrimony;
import br.com.almeida.managerpatrimony.persistence.repository.PatrimonyRepository;
import br.com.almeida.managerpatrimony.service.mapper.Mapper;

@Service
public class PatrimonyServiceImpl implements PatrimonyService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PatrimonyService.class);

	@Autowired
	private PatrimonyRepository patrimonyRepository;

	@Autowired
	private Mapper<PatrimonyRequest, Patrimony> patrimonyRequestMapper;

	@Autowired
	private Mapper<Patrimony, PatrimonyReponse> patrimonyResponseMapper;

	@Override
	@Transactional
	public PatrimonyReponse create(PatrimonyRequest request) {
		
		notNull(request, "patrimony must not be null");

		Patrimony patrimony = this.patrimonyRequestMapper.map(request);

		Long lastNumberFall = patrimonyRepository.lastNumberFall();

		if (lastNumberFall != null) {
			patrimony.setNumberFall(Long.sum(lastNumberFall, BigInteger.ONE.longValue()));
		}

		return patrimonyRepository.save(patrimony).map(this.patrimonyResponseMapper::map);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<PatrimonyReponse> getAll(Pageable page) {
		
		notNull(page, "Invalid page");

		return patrimonyRepository.findAll(page).map(this.patrimonyResponseMapper::map);
	}

	@Override
	@Transactional
	public Optional<PatrimonyReponse> update(Long id, PatrimonyRequest request) {
		
		notNull(id, "Invalid patrimony id");
        state(id > 0, "Invalid patrimony id, must be greater zero");
        notNull(request, "Invalid patrimony");

		Patrimony patrimony = this.patrimonyRequestMapper.map(request);

		notNull(patrimony, "Invalid patrimony");

		return patrimonyRepository.findById(id).map(it -> {
			it.setName(patrimony.getName());
			it.setDescription(patrimony.getDescription());
			it.setBrand(patrimony.getBrand());

			return this.patrimonyResponseMapper.map(patrimonyRepository.save(it));
		});
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<PatrimonyReponse> getId(Long id) {
		
		notNull(id, "Invalid patrimony id");
		state(id > 0, "Invalid patrimony id, must be greater zero");

		return patrimonyRepository.findById(id).map(this.patrimonyResponseMapper::map);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<PatrimonyReponse> getName(String name) {
		
		notNull(name, "Invalid patrimony name");

		return patrimonyRepository.findByName(name).map(this.patrimonyResponseMapper::map);
	}

	@Override
	@Transactional
	public boolean delete(Long id) {
		
		notNull(id, "Invalid patrimony id");
		state(id > 0, "Invalid patrimony id, must be greater zero");

		try {
			patrimonyRepository.deleteById(id);
			return true;
		} catch (Exception ex) {
			LOGGER.warn("Error on delete Patrimony with id {}", id, ex);
		}
		return false;
	}

	@Override
	@Transactional(readOnly = true)
	public List<PatrimonyReponse> getAllByBrandId(Long brandId) {

		return patrimonyRepository.findAllByBrandIdOrderByDescriptionAsc(brandId).stream()
				.map(this.patrimonyResponseMapper::map).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<PatrimonyReponse> getByNumberFall(Long numberFall) {
		
		notNull(numberFall, "Invalid patrimony numberFall");
		state(numberFall > 0, "Invalid patrimony numberFall, must be greater zero");

		return patrimonyRepository.findByNumberFall(numberFall).map(this.patrimonyResponseMapper::map);
	}

}
