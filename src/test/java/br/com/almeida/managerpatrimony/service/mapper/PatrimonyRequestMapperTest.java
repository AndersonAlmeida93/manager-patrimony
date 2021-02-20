package br.com.almeida.managerpatrimony.service.mapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.almeida.managerpatrimony.model.request.PatrimonyRequest;
import br.com.almeida.managerpatrimony.persistence.entity.Patrimony;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class PatrimonyRequestMapperTest {

	@Autowired
	private PatrimonyRequestMapper mapper;
	
	@BeforeAll
	public static void setup() {
		FixtureFactoryLoader.loadTemplates("br.com.almeida.managerpatrimony.fixture");
	}
	
	@Test
	public void shouldCreatePatrimony() {
		PatrimonyRequest patrimonyRequest = Fixture.from(PatrimonyRequest.class).gimme("valid");
		assertThat(patrimonyRequest, notNullValue());
		assertThat(patrimonyRequest.getName(), notNullValue());
		assertThat(patrimonyRequest.getDescription(), notNullValue());
		
		Patrimony patrimony = mapper.map(patrimonyRequest);
		
		assertThat(patrimony, notNullValue());
		assertThat(patrimony.getName(), notNullValue());
		assertThat(patrimony.getDescription(), notNullValue());
		
	}
}
