package br.com.almeida.managerpatrimony.service.mapper;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.almeida.managerpatrimony.model.request.UserRequest;
import br.com.almeida.managerpatrimony.persistence.entity.Users;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

@SpringBootTest
public class UserRequestMapperTest {

	@Autowired
	private UserRequestMapper mapper;

	@BeforeAll
	public static void setup() {
		FixtureFactoryLoader.loadTemplates("br.com.almeida.managerpatrimony.fixture");
	}

	@Test
	public void createUser() {
		UserRequest userRequest = Fixture.from(UserRequest.class).gimme("valid");
		assertThat(userRequest, notNullValue());
		assertThat(userRequest.getName(), notNullValue());
		assertThat(userRequest.getEmail(), notNullValue());

		Users users = mapper.map(userRequest);

		assertThat(users, notNullValue());
		assertThat(users.getName(), notNullValue());
		assertThat(users.getEmail(), notNullValue());

	}
}
