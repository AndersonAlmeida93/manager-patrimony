package br.com.almeida.managerpatrimony.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserRequest {

	@NotBlank
	@Size(max = 50)
	private String name;
	
	@NotBlank
	@Size(max = 30)
	private String email;
	
	@NotBlank
	@Size(max = 30)
	private String password;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
