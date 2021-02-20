package br.com.almeida.managerpatrimony.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.almeida.managerpatrimony.model.request.BrandRequest;
import br.com.almeida.managerpatrimony.model.response.BrandResponse;
import br.com.almeida.managerpatrimony.persistence.entity.Brand;
import br.com.almeida.managerpatrimony.persistence.repository.BrandRepository;
import br.com.almeida.managerpatrimony.service.mapper.Mapper;

@SpringBootTest
public class BrandServiceImplTest {

	@Autowired
	private BrandServiceImpl serviceImpl;

	@MockBean
	private BrandRepository repository;

	@MockBean
	private Mapper<BrandRequest, Brand> requestMapper;

	@MockBean
	private Mapper<Brand, BrandResponse> responseMapper;

	@Test
	public void shouldCreateBrand() {
		var response = getBrandResponse();
		var request = getBrandRequest();
		var brand = getBrand();

		when(requestMapper.map(request)).thenReturn(brand);
		when(repository.save(brand)).thenReturn(brand);
		when(responseMapper.map(brand)).thenReturn(response);

		var brandResponse = serviceImpl.create(request);
		checkBrandResponse(response, brandResponse);

		verify(requestMapper).map(eq(request));
		verify(repository, never()).findById(brand.getId());
		verify(repository).save(brand);
		verify(responseMapper).map(brand);
	}

	@Test
	public void shouldThrowExceptionWhenRequestIsNullCreate() {
		var ex = assertThrows(IllegalArgumentException.class, () -> serviceImpl.create(null));
		assertNotNull(ex);
		assertEquals("brand must not be null", ex.getMessage());

		verify(requestMapper, never()).map(any());
		verify(repository, never()).findById(anyLong());
		verify(repository, never()).save(any());
		verify(responseMapper, never()).map(any());
	}

	@Test
	public void shouldGetAll() {
		var brand = getBrand();
		var pageBrand = new PageImpl<>(Arrays.asList(brand));
		var response = getBrandResponse();
		var page = mock(Pageable.class);

		when(repository.findAll(page)).thenReturn(pageBrand);
		when(responseMapper.map(brand)).thenReturn(response);

		var responses = serviceImpl.getAll(page);
		assertNotNull(responses);
		assertEquals(1, responses.getTotalElements());
		assertEquals(1, responses.getTotalPages());

		checkBrandResponse(response, responses.getContent().get(0));

		verify(repository).findAll(page);
		verify(responseMapper).map(brand);
	}

	@Test
	public void shouldThrowExceptionWhenPageableIsNullGetAll() {
		var ex = assertThrows(IllegalArgumentException.class, () -> serviceImpl.getAll(null));
		assertNotNull(ex);
		assertEquals("Invalid page", ex.getMessage());

		verify(repository, never()).findAll(any(Pageable.class));
		verify(responseMapper, never()).map(any());
	}

	@Test
	public void shouldUpdateBrand() {
		var id = 1L;
		var request = getBrandRequest();
		var brand = getBrand();
		var response = getBrandResponse();

		when(requestMapper.map(request)).thenReturn(brand);
		when(repository.findById(id)).thenReturn(Optional.of(brand));
		when(repository.save(brand)).thenReturn(brand);
		when(responseMapper.map(brand)).thenReturn(response);

		var brandResponse = serviceImpl.update(id, request);
		assertTrue(brandResponse.isPresent());

		checkBrandResponse(response, brandResponse.get());

		verify(requestMapper).map(eq(request));
		verify(repository).findById(eq(id));
		verify(repository).save(eq(brand));
		verify(responseMapper).map(eq(brand));
	}

	@Test
	public void shouldThrowExceptionWhenBrandIsNullUpdate() {
		var request = getBrandRequest();

		when(requestMapper.map(request)).thenReturn(null);

		var ex = assertThrows(IllegalArgumentException.class, () -> serviceImpl.update(1L, request));
		assertNotNull(ex);
		assertEquals("Invalid brand", ex.getMessage());

		verify(requestMapper).map(request);
		verify(repository, never()).findById(anyLong());
		verify(repository, never()).save(any());
		verify(responseMapper, never()).map(any());
	}

	@Test
	public void shouldThrowExceptionInvalidIdUpdate() {
		Exception ex = assertThrows(IllegalArgumentException.class,
				() -> serviceImpl.update(null, mock(BrandRequest.class)));
		assertNotNull(ex);
		assertEquals("Invalid brand id", ex.getMessage());

		ex = assertThrows(IllegalStateException.class, () -> serviceImpl.update(0L, mock(BrandRequest.class)));
		assertNotNull(ex);
		assertEquals("Invalid brand id, must be greater zero", ex.getMessage());

		verify(repository, never()).findById(anyLong());
		verify(repository, never()).save(any());
		verify(responseMapper, never()).map(any());
	}

	@Test
	public void shouldThrowExceptionNullRequestUpdate() {
		var ex = assertThrows(IllegalArgumentException.class, () -> serviceImpl.update(1L, null));
		assertNotNull(ex);
		assertEquals("Invalid brand", ex.getMessage());

		verify(repository, never()).save(any());
		verify(responseMapper, never()).map(any());
	}

	@Test
	public void shouldGetBrand() {
		var id = 1L;
		var brand = getBrand();
		var response = getBrandResponse();

		when(repository.findById(id)).thenReturn(Optional.of(brand));
		when(responseMapper.map(brand)).thenReturn(response);

		var brandResponse = serviceImpl.getId(id);
		assertTrue(brandResponse.isPresent());

		checkBrandResponse(response, brandResponse.get());

		verify(repository).findById(eq(id));
		verify(responseMapper).map(eq(brand));
	}

	@Test
	public void shouldThrowExceptionInvalidIdGet() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> serviceImpl.getId(null));
		assertNotNull(ex);
		assertEquals("Invalid brand id", ex.getMessage());

		ex = assertThrows(IllegalStateException.class, () -> serviceImpl.getId(0L));
		assertNotNull(ex);
		assertEquals("Invalid brand id, must be greater zero", ex.getMessage());

		verify(repository, never()).findById(anyLong());
		verify(responseMapper, never()).map(any());
	}

	@Test
	public void shouldGetBrandByName() {
		var name = "Nike";
		var brand = getBrand();
		var response = getBrandResponse();

		when(repository.findByName(name)).thenReturn(Optional.of(brand));
		when(responseMapper.map(brand)).thenReturn(response);

		var brandResponse = serviceImpl.getName(name);
		assertTrue(brandResponse.isPresent());

		checkBrandResponse(response, brandResponse.get());

		verify(repository).findByName(eq(name));
		verify(responseMapper).map(eq(brand));
	}

	@Test
	public void shouldThrowExceptionNullName() {
		var ex = assertThrows(IllegalArgumentException.class, () -> serviceImpl.getName(null));
		assertNotNull(ex);
		assertEquals("Invalid brand name", ex.getMessage());

		verify(repository, never()).findByName(anyString());
		verify(responseMapper, never()).map(any());
	}

	@Test
	public void shouldDeleteBrand() {
		var id = 1L;
		var brand = getBrand();

		when(repository.findById(id)).thenReturn(Optional.of(brand));

		assertTrue(serviceImpl.delete(id));
	}

	@Test
	public void shouldThrowExceptionInvalidIdDelete() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> serviceImpl.delete(null));
		assertNotNull(ex);
		assertEquals("Invalid brand id", ex.getMessage());

		ex = assertThrows(IllegalStateException.class, () -> serviceImpl.delete(0L));
		assertNotNull(ex);
		assertEquals("Invalid brand id, must be greater zero", ex.getMessage());

		verify(repository, never()).findById(anyLong());
	}

	private void checkBrandResponse(BrandResponse expected, BrandResponse actual) {
		assertNotNull(actual);
		assertEquals(expected, actual);
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getName(), actual.getName());
	}

	private BrandRequest getBrandRequest() {
		var request = new BrandRequest();
		request.setName("Nike");
		return request;
	}

	private BrandResponse getBrandResponse() {
		var response = new BrandResponse();
		response.setName("Nike");
		return response;
	}

	private Brand getBrand() {
		var brand = new Brand();
		brand.setId(1L);
		brand.setName("Nike");
		return brand;
	}
}
