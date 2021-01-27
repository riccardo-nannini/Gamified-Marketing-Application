package it.polimi.db2.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: User1
 *
 */
@Entity
@Table(name = "user", schema = "gamified_db")


public class User implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String username;

	private String password;

	private String email;

	//POTREBBE ,DOVREBBE ESSERE UN ENUM
	private String role;
	
	private Boolean blocked;
	

	

	public int getId() {
		return id;
	}




	public void setId(int id) {
		this.id = id;
	}




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




	public Boolean getBlocked() {
		return blocked;
	}




	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}



	public User() {
		super();
	}
   
}
