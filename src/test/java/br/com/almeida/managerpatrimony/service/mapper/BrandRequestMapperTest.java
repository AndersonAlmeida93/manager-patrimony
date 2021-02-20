package br.com.almeida.managerpatrimony.service.mapper;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.almeida.managerpatrimony.model.request.BrandRequest;
import br.com.almeida.managerpatrimony.persistence.entity.Brand;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

@SpringBootTest
public class BrandRequestMapperTest {

	@Autowired
	private BrandRequestMapper mapper;
	
	@BeforeAll
	public static void setup() {
		FixtureFactoryLoader.loadTemplates("br.com.almeida.managerpatrimony.fixture");
	}
	
	@Test
	public void shouldCreateBrand() {
		BrandRequest brandRequest = Fixture.from(BrandRequest.class).gimme("valid");
		assertThat(brandRequest, notNullValue());
		assertThat(brandRequest.getName(), notNullValue());
		
		Brand brand = mapper.map(brandRequest);
		
		assertThat(brand, notNullValue());
		assertThat(brand.getName(), notNullValue());
	}
}
