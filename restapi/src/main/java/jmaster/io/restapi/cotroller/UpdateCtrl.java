package jmaster.io.restapi.cotroller;

import java.util.Set;

public class UpdateCtrl {
	private int user_id;
	private String username;
	private String password;
	private Set<Integer> role_id;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public Set<Integer> getRole_id() {
		return role_id;
	}
	public void setRole_id(Set<Integer> role_id) {
		this.role_id = role_id;
	}
	public UpdateCtrl(int user_id, String username, String password, Set<Integer> role_id) {
		super();
		this.user_id = user_id;
		this.username = username;
		this.password = password;
		this.role_id = role_id;
	}
	
	
}
