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

import br.com.almeida.managerpatrimony.model.request.PatrimonyRequest;
import br.com.almeida.managerpatrimony.model.response.PatrimonyReponse;
import br.com.almeida.managerpatrimony.persistence.entity.Brand;
import br.com.almeida.managerpatrimony.persistence.entity.Patrimony;
import br.com.almeida.managerpatrimony.persistence.repository.PatrimonyRepository;
import br.com.almeida.managerpatrimony.service.mapper.Mapper;

@SpringBootTest
public class PatrimonyServiceImplTest {

	@Autowired
	private PatrimonyServiceImpl serviceImpl;

	@MockBean
	private PatrimonyRepository repository;

	@MockBean
	private Mapper<PatrimonyRequest, Patrimony> requestMapper;

	@MockBean
	private Mapper<Patrimony, PatrimonyReponse> responseMapper;

	@Test
	public void shouldCreatePatrimony() {
		var response = getPatrimonyReponse();
		var request = getPatrimonyRequest();
		var patrimony = getPatrimony();

		when(requestMapper.map(request)).thenReturn(patrimony);
		when(repository.save(patrimony)).thenReturn(patrimony);
		when(responseMapper.map(patrimony)).thenReturn(response);

		var taskResponse = serviceImpl.create(request);
		checkPatrimonyResponse(response, taskResponse);

		verify(requestMapper).map(eq(request));
		verify(repository, never()).findById(patrimony.getId());
		verify(repository).save(eq(patrimony));
		verify(responseMapper).map(eq(patrimony));
	}

	@Test
	public void shouldThrowExceptionWhenRequestIsNullCreate() {
		var ex = assertThrows(IllegalArgumentException.class, () -> serviceImpl.create(null));
		assertNotNull(ex);
		assertEquals("patrimony must not be null", ex.getMessage());

		verify(requestMapper, never()).map(any());
		verify(repository, never()).findById(anyLong());
		verify(repository, never()).save(any());
		verify(responseMapper, never()).map(any());
	}

	@Test
	public void shouldGetAll() {
		var patrimony = getPatrimony();
		var pagePatrimony = new PageImpl<>(Arrays.asList(patrimony));
		var response = getPatrimonyReponse();
		var page = mock(Pageable.class);

		when(repository.findAll(page)).thenReturn(pagePatrimony);
		when(responseMapper.map(patrimony)).thenReturn(response);

		var responses = serviceImpl.getAll(page);
		assertNotNull(responses);
		assertEquals(1, responses.getTotalElements());
		assertEquals(1, responses.getTotalPages());

		checkPatrimonyResponse(response, responses.getContent().get(0));

		verify(repository).findAll(page);
		verify(responseMapper).map(patrimony);
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
		var request = getPatrimonyRequest();
		var patrimony = getPatrimony();
		var response = getPatrimonyReponse();

		when(requestMapper.map(request)).thenReturn(patrimony);
		when(repository.findById(id)).thenReturn(Optional.of(patrimony));
		when(repository.save(patrimony)).thenReturn(patrimony);
		when(responseMapper.map(patrimony)).thenReturn(response);

		var brandResponse = serviceImpl.update(id, request);
		assertTrue(brandResponse.isPresent());

		checkPatrimonyResponse(response, brandResponse.get());

		verify(requestMapper).map(eq(request));
		verify(repository).findById(eq(id));
		verify(repository).save(eq(patrimony));
		verify(responseMapper).map(eq(patrimony));
	}

	@Test
	public void shouldThrowExceptionWhenPatrimonyIsNullUpdate() {
		var request = getPatrimonyRequest();

		when(requestMapper.map(request)).thenReturn(null);

		var ex = assertThrows(IllegalArgumentException.class, () -> serviceImpl.update(1L, request));
		assertNotNull(ex);
		assertEquals("Invalid patrimony", ex.getMessage());

		verify(requestMapper).map(request);
		verify(repository, never()).findById(anyLong());
		verify(repository, never()).save(any());
		verify(responseMapper, never()).map(any());
	}

	@Test
	public void shouldThrowExceptionInvalidIdUpdate() {
		Exception ex = assertThrows(IllegalArgumentException.class,
				() -> serviceImpl.update(null, mock(PatrimonyRequest.class)));
		assertNotNull(ex);
		assertEquals("Invalid patrimony id", ex.getMessage());

		ex = assertThrows(IllegalStateException.class, () -> serviceImpl.update(0L, mock(PatrimonyRequest.class)));
		assertNotNull(ex);
		assertEquals("Invalid patrimony id, must be greater zero", ex.getMessage());

		verify(repository, never()).findById(anyLong());
		verify(repository, never()).save(any());
		verify(responseMapper, never()).map(any());
	}

	@Test
	public void shouldThrowExceptionNullRequestUpdate() {
		var ex = assertThrows(IllegalArgumentException.class, () -> serviceImpl.update(1L, null));
		assertNotNull(ex);
		assertEquals("Invalid patrimony", ex.getMessage());

		verify(repository, never()).save(any());
		verify(responseMapper, never()).map(any());
	}

	@Test
	public void shouldGetBrand() {
		var id = 1L;
		var patrimony = getPatrimony();
		var response = getPatrimonyReponse();

		when(repository.findById(id)).thenReturn(Optional.of(patrimony));
		when(responseMapper.map(patrimony)).thenReturn(response);

		var brandResponse = serviceImpl.getId(id);
		assertTrue(brandResponse.isPresent());

		checkPatrimonyResponse(response, brandResponse.get());

		verify(repository).findById(eq(id));
		verify(responseMapper).map(eq(patrimony));
	}

	@Test
	public void shouldThrowExceptionInvalidIdGet() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> serviceImpl.getId(null));
		assertNotNull(ex);
		assertEquals("Invalid patrimony id", ex.getMessage());

		ex = assertThrows(IllegalStateException.class, () -> serviceImpl.getId(0L));
		assertNotNull(ex);
		assertEquals("Invalid patrimony id, must be greater zero", ex.getMessage());

		verify(repository, never()).findById(anyLong());
		verify(responseMapper, never()).map(any());
	}

	@Test
	public void shouldGetBrandByName() {
		var name = "t-shirt";
		var patrimony = getPatrimony();
		var response = getPatrimonyReponse();

		when(repository.findByName(name)).thenReturn(Optional.of(patrimony));
		when(responseMapper.map(patrimony)).thenReturn(response);

		var brandResponse = serviceImpl.getName(name);
		assertTrue(brandResponse.isPresent());

		checkPatrimonyResponse(response, brandResponse.get());

		verify(repository).findByName(eq(name));
		verify(responseMapper).map(eq(patrimony));
	}

	@Test
	public void shouldThrowExceptionNullName() {
		var ex = assertThrows(IllegalArgumentException.class, () -> serviceImpl.getName(null));
		assertNotNull(ex);
		assertEquals("Invalid patrimony name", ex.getMessage());

		verify(repository, never()).findByName(anyString());
		verify(responseMapper, never()).map(any());
	}

	@Test
	public void shouldDeleteBrand() {
		var id = 1L;
		var patrimony = getPatrimony();

		when(repository.findById(id)).thenReturn(Optional.of(patrimony));

		assertTrue(serviceImpl.delete(id));
	}

	@Test
	public void shouldThrowExceptionInvalidIdDelete() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> serviceImpl.delete(null));
		assertNotNull(ex);
		assertEquals("Invalid patrimony id", ex.getMessage());

		ex = assertThrows(IllegalStateException.class, () -> serviceImpl.delete(0L));
		assertNotNull(ex);
		assertEquals("Invalid patrimony id, must be greater zero", ex.getMessage());

		verify(repository, never()).findById(anyLong());
	}

	@Test
	public void shouldPatrimonyByNumberFall() {
		var numberFall = 110L;
		var patrimony = getPatrimony();
		var response = getPatrimonyReponse();

		when(repository.findByNumberFall(numberFall)).thenReturn(Optional.of(patrimony));
		when(responseMapper.map(patrimony)).thenReturn(response);

		var patrimonyResponse = serviceImpl.getByNumberFall(numberFall);
		assertTrue(patrimonyResponse.isPresent());

		checkPatrimonyResponse(response, patrimonyResponse.get());

		verify(repository).findByNumberFall(eq(numberFall));
		verify(responseMapper).map(eq(patrimony));
	}

	@Test
	public void shouldThrowExceptionInvalidIdNumberFall() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> serviceImpl.getByNumberFall(null));
		assertNotNull(ex);
		assertEquals("Invalid patrimony numberFall", ex.getMessage());

		ex = assertThrows(IllegalStateException.class, () -> serviceImpl.getByNumberFall(0L));
		assertNotNull(ex);
		assertEquals("Invalid patrimony numberFall, must be greater zero", ex.getMessage());

		verify(repository, never()).findByNumberFall(anyLong());
		verify(responseMapper, never()).map(any());
	}

	private void checkPatrimonyResponse(PatrimonyReponse expected, PatrimonyReponse actual) {
		assertNotNull(actual);
		assertEquals(expected, actual);
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getDescription(), actual.getDescription());
		assertEquals(expected.getNumberFall(), actual.getNumberFall());
		assertEquals(expected.getBrandId(), actual.getBrandId());
	}

	private PatrimonyRequest getPatrimonyRequest() {
		var request = new PatrimonyRequest();
		request.setName("t-shirt");
		request.setDescription("t-shirt for surf");
		return request;
	}

	private PatrimonyReponse getPatrimonyReponse() {
		var response = new PatrimonyReponse();
		response.setName("t-shirt");
		response.setDescription("t-shirt for surf");
		response.setBrandId(1L);
		response.setNumberFall(110L);
		return response;
	}

	private Patrimony getPatrimony() {
		var patrimony = new Patrimony();
		patrimony.setId(1L);
		patrimony.setName("t-shirt");
		patrimony.setDescription("t-shirt for surf");
		patrimony.setBrand(getBrand());
		return patrimony;
	}

	private Brand getBrand() {
		var brand = new Brand();
		brand.setId(1L);
		brand.setName("Nike");
		return brand;
	}
}
