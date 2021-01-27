package it.polimi.db2.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: QuestionnaireAnswer
 *
 */
@Entity
@Table(name = "questionnarieanswer", schema = "gamified_db")
public class QuestionnaireAnswer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int answ1;
	
	private String answ2;
	
	private String answ3;
	
	private boolean deleted;
	
	@ManyToOne
	@JoinColumn(name = "userID")
	private User user;
	
	//@ManyToOne
	//@JoinColumn(name = "userID")
	//private  product;
	

	public QuestionnaireAnswer() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAnsw1() {
		return answ1;
	}

	public void setAnsw1(int answ1) {
		this.answ1 = answ1;
	}

	public String getAnsw2() {
		return answ2;
	}

	public void setAnsw2(String answ2) {
		this.answ2 = answ2;
	}

	public String getAnsw3() {
		return answ3;
	}

	public void setAnsw3(String answ3) {
		this.answ3 = answ3;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
   
}
