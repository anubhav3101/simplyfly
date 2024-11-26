package com.simplyfly.dto;
public class JWTAuthResponse {
	private String accessToken;
	private String tokenType = "Bearer";
	private UserDTO UserDTO;// appending user details and JWT Token in response

	public JWTAuthResponse() {
	}

	public JWTAuthResponse(String accessToken, UserDTO UserDTO) {
		super();
		this.accessToken = accessToken;
		this.UserDTO=UserDTO;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public UserDTO getUserDto() {
		return UserDTO;
	}

	public void setUserDto(UserDTO UserDTO) {
		this.UserDTO=UserDTO;
	}
}
