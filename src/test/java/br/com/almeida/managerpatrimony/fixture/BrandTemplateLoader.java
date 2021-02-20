package br.com.almeida.managerpatrimony.fixture;

import br.com.almeida.managerpatrimony.model.request.BrandRequest;
import br.com.almeida.managerpatrimony.model.response.BrandResponse;
import br.com.almeida.managerpatrimony.persistence.entity.Brand;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class BrandTemplateLoader implements TemplateLoader{

	@Override
	public void load() {
		
		Fixture.of(BrandRequest.class).addTemplate("valid" , new Rule() {{
			add("name", name());
		}});
		
		Fixture.of(Brand.class).addTemplate("valid" , new Rule() {{
			add("name", name());
		}});
		
		Fixture.of(BrandResponse.class).addTemplate("valid" , new Rule() {{
			add("id", random(Long.class, range(1L, 200L)));
			add("name", name());
		}});
		
	}

}
