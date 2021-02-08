package it.polimi.db2.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "user", schema = "gamified_db")
@NamedQuery(name = "User.checkCredentials", query = "SELECT r FROM User r  WHERE r.username = ?1 and r.password = ?2")
public class User implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String username;

	private String password;

	private String email;

	private String role;
	
	private Boolean blocked;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.MERGE})
	private List<QuestionnaireAnswer> answers;
	

	public User() {
		super();
	}
	
	public User(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = "User";
		this.blocked = false;
	} 
	


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

	public List<QuestionnaireAnswer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<QuestionnaireAnswer> answers) {
		this.answers = answers;
	}

   
}
