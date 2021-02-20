package br.com.almeida.managerpatrimony.persistence.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "brand")
public class Brand {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "brand")
	private List<Patrimony> patrimonys = new ArrayList<Patrimony>();
	
	public <R> R map (Function<Brand, R> func) {
		return func.apply(this);
	}

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

	public List<Patrimony> getPatrimonys() {
		return patrimonys;
	}

	public void setPatrimonys(List<Patrimony> patrimonys) {
		this.patrimonys = patrimonys;
	}
	
	
	
}
