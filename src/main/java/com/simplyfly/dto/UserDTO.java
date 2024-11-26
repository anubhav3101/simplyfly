package com.simplyfly.dto;

import jakarta.validation.constraints.NotBlank;

public class UserDTO {

    private Long id;
    private String username;
    private String name;
    @NotBlank
    private String email;
    private String role;
    @NotBlank
    private String password;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserDTO(Long id, String username, String name, @NotBlank String email, String role,
			@NotBlank String password) {
		super();
		this.id = id;
		this.username = username;
		this.name = name;
		this.email = email;
		this.role = role;
		this.password = password;
	}
	public UserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

    
    
}