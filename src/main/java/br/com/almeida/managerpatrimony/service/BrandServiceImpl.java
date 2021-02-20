package br.com.almeida.managerpatrimony.service;

import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;
import static org.springframework.util.Assert.state;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.almeida.managerpatrimony.model.request.BrandRequest;
import br.com.almeida.managerpatrimony.model.response.BrandResponse;
import br.com.almeida.managerpatrimony.persistence.entity.Brand;
import br.com.almeida.managerpatrimony.persistence.repository.BrandRepository;
import br.com.almeida.managerpatrimony.service.mapper.Mapper;

@Service
public class BrandServiceImpl implements BrandService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BrandService.class);

	@Autowired
	private BrandRepository brandRepository;

	@Autowired
	private Mapper<BrandRequest, Brand> brandRequestMapper;

	@Autowired
	private Mapper<Brand, BrandResponse> brandResponseMapper;

	@Override
	@Transactional
	public BrandResponse create(BrandRequest request) {

		notNull(request, "brand must not be null");

		Optional<Brand> byName = brandRepository.findByName(request.getName());

		isTrue(!byName.isPresent(), "Brand already exists");

		Brand brand = this.brandRequestMapper.map(request);

		return brandRepository.save(brand).map(this.brandResponseMapper::map);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<BrandResponse> getAll(Pageable page) {

		notNull(page, "Invalid page");

		return brandRepository.findAll(page).map(this.brandResponseMapper::map);
	}

	@Override
	@Transactional
	public Optional<BrandResponse> update(Long id, BrandRequest request) {

		notNull(id, "Invalid brand id");
		state(id > 0, "Invalid brand id, must be greater zero");
		notNull(request, "Invalid brand");

		Brand brand = this.brandRequestMapper.map(request);

		notNull(brand, "Invalid brand");

		return brandRepository.findById(id).map(it -> {
			it.setName(brand.getName());

			return this.brandResponseMapper.map(brandRepository.save(it));
		});
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<BrandResponse> getId(Long id) {

		notNull(id, "Invalid brand id");
		state(id > 0, "Invalid brand id, must be greater zero");

		return brandRepository.findById(id).map(this.brandResponseMapper::map);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<BrandResponse> getName(String name) {

		notNull(name, "Invalid brand name");

		return brandRepository.findByName(name).map(this.brandResponseMapper::map);
	}

	@Override
	@Transactional
	public boolean delete(Long id) {

		notNull(id, "Invalid brand id");
		state(id > 0, "Invalid brand id, must be greater zero");

		try {
			brandRepository.deleteById(id);
			return true;
		} catch (Exception ex) {
			LOGGER.warn("Error on delete brand with id {}", id, ex);
		}
		return false;
	}

}
