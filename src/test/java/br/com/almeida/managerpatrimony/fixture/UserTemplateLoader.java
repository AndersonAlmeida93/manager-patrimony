package br.com.almeida.managerpatrimony.fixture;

import br.com.almeida.managerpatrimony.model.request.UserRequest;
import br.com.almeida.managerpatrimony.model.response.UserResponse;
import br.com.almeida.managerpatrimony.persistence.entity.Users;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class UserTemplateLoader implements TemplateLoader{

	@Override
	public void load() {
		
		Fixture.of(UserRequest.class).addTemplate("valid", new Rule() {{
			add("name", name());
			add("email", "teste@email.com");
		}});
		
		Fixture.of(Users.class).addTemplate("valid", new Rule() {{
			add("name", name());
			add("email", "teste@email.com");
		}});
		
		Fixture.of(UserResponse.class).addTemplate("valid", new Rule() {{
			add("name", name());
			add("email", "teste@email.com");
		}});
	}

}
