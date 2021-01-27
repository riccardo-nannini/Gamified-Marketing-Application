package it.polimi.db2.entities;

import java.io.Serializable;
import java.security.Timestamp;

import javax.persistence.*;


@Entity
@Table(name = "log", schema = "gamified_db")
public class Log implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private Timestamp timestamp;
	
	@ManyToOne
	@JoinColumn(name = "userID")
	private User user;

	public Log() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
   
}
