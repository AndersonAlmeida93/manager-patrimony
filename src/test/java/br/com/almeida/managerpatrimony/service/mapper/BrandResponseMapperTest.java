package br.com.almeida.managerpatrimony.service.mapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.almeida.managerpatrimony.model.response.BrandResponse;
import br.com.almeida.managerpatrimony.persistence.entity.Brand;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class BrandResponseMapperTest {

	@Autowired
	private BrandResponseMapper mapper;
	
	@BeforeAll
	public static void setup() {
		FixtureFactoryLoader.loadTemplates("br.com.almeida.managerpatrimony.fixture");
	}
	
	@Test
	public void shouldCreateBrand() {
		Brand brand = Fixture.from(Brand.class).gimme("valid");
		assertThat(brand, notNullValue());
		assertThat(brand.getName(), notNullValue());
		
		BrandResponse response = mapper.map(brand);
		
		assertThat(response, notNullValue());
		assertThat(response.getName(), notNullValue());
	}
}
