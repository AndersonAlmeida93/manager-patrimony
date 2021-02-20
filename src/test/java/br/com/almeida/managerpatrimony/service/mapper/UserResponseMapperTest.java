package br.com.almeida.managerpatrimony.service.mapper;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.almeida.managerpatrimony.model.response.UserResponse;
import br.com.almeida.managerpatrimony.persistence.entity.Users;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

@SpringBootTest
public class UserResponseMapperTest {

	@Autowired
	private UserResponseMapper mapper;

	@BeforeAll
	public static void setup() {
		FixtureFactoryLoader.loadTemplates("br.com.almeida.managerpatrimony.fixture");
	}

	@Test
	public void createUser() {
		Users users = Fixture.from(Users.class).gimme("valid");
		assertThat(users, notNullValue());
		assertThat(users.getName(), notNullValue());
		assertThat(users.getEmail(), notNullValue());

		UserResponse response = mapper.map(users);

		assertThat(response, notNullValue());
		assertThat(response.getName(), notNullValue());
		assertThat(response.getEmail(), notNullValue());

	}
}
