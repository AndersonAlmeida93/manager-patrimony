package br.com.almeida.managerpatrimony.fixture;

import br.com.almeida.managerpatrimony.model.request.PatrimonyRequest;
import br.com.almeida.managerpatrimony.model.response.PatrimonyReponse;
import br.com.almeida.managerpatrimony.persistence.entity.Patrimony;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class PatrimonyTemplateLoader implements TemplateLoader{

	@Override
	public void load() {

		Fixture.of(PatrimonyRequest.class).addTemplate("valid", new Rule() {{
			add("name", name());
			add("description", name());
		}});
		
		Fixture.of(Patrimony.class).addTemplate("valid", new Rule() {{
			add("name", name());
			add("description", name());
		}});
		
		Fixture.of(PatrimonyReponse.class).addTemplate("valid", new Rule() {{
			add("name", name());
			add("description", name());
		}});
	}

}
