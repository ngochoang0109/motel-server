package com.server.kltn.motel.api.authentication.payload;

import java.util.List;

public class JwtAuthentication {
	
	private String accessToken;
    private String tokenType = "Bearer";
    private String usernameOrEmail;
    private List<String> roles;

	public JwtAuthentication(String accessToken, String usernameOrEmail, List<String> roles) {
		super();
		this.accessToken = accessToken;
		this.usernameOrEmail = usernameOrEmail;
		this.roles = roles;
	}
	public JwtAuthentication(String accessToken) {
		super();
		this.accessToken = accessToken;
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
	public String getUsernameOrEmail() {
		return usernameOrEmail;
	}
	public void setUsernameOrEmail(String usernameOrEmail) {
		this.usernameOrEmail = usernameOrEmail;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}
