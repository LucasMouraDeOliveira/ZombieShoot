package com.lordkadoc.commons.networkobjects;

public class ConnectionAttempt {

	private String login;
	
	private int skinId;
	
	public ConnectionAttempt(){
		
	}
	
	public int getSkinId() {
		return skinId;
	}
	
	public void setSkinId(int skinId) {
		this.skinId = skinId;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getLogin() {
		return login;
	}
	
}
