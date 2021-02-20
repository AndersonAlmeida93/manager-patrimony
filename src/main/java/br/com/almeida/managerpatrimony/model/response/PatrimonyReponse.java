package br.com.almeida.managerpatrimony.model.response;

public class PatrimonyReponse {

	private Long id;
	
	private String name;
	
	private String description;
	
	private Long numberFall;
	
	private Long brandId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getNumberFall() {
		return numberFall;
	}

	public void setNumberFall(Long numberFall) {
		this.numberFall = numberFall;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
}
