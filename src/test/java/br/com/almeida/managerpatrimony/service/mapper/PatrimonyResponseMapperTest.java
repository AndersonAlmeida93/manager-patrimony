package br.com.almeida.managerpatrimony.service.mapper;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.almeida.managerpatrimony.model.response.PatrimonyReponse;
import br.com.almeida.managerpatrimony.persistence.entity.Patrimony;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

@SpringBootTest
public class PatrimonyResponseMapperTest {

	@Autowired
	private PatrimonyResponseMapper mapper;

	@BeforeAll
	public static void setup() {
		FixtureFactoryLoader.loadTemplates("br.com.almeida.managerpatrimony.fixture");
	}

	@Test
	public void shouldCreatePatrimony() {
		Patrimony patrimony = Fixture.from(Patrimony.class).gimme("valid");
		assertThat(patrimony, notNullValue());
		assertThat(patrimony.getName(), notNullValue());
		assertThat(patrimony.getDescription(), notNullValue());

		PatrimonyReponse reponse = mapper.map(patrimony);

		assertThat(reponse, notNullValue());
		assertThat(reponse.getName(), notNullValue());
		assertThat(reponse.getDescription(), notNullValue());
	}
}
