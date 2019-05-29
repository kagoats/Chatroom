package main;

import java.io.Serializable;

import encryption.SHA256;

public class Account implements Serializable {

	private static final long serialVersionUID = -7853582653929790549L;

	private String username;
	private String showName;
	//TODO make password into passwordHash
	private byte[] passwordHash;
	
	
	public Account(String username, String password) {
		this(username, password, null);
	}
	public Account(String username, String password, String showName) {
		this.username = username;
		this.showName = (showName == null ? username : showName);
		
		this.passwordHash = SHA256.hash(password);
	}

	public String getUsername() {
		return username;
	}

	public String getShowName() {
		return showName;
	}
	
	public byte[] getPasswordHash() {
		return passwordHash;
	}
}
