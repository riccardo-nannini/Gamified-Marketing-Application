package it.polimi.db2.entities;

import java.io.Serializable;
import java.security.Timestamp;
import java.util.Date;

import javax.persistence.*;


@Entity
@Table(name = "log", schema = "gamified_db")
@NamedQuery(name = "Log.findAll", query = "SELECT l FROM Log l")
public class Log implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date timestamp;
	
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

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(java.sql.Date timestamp) {
		this.timestamp = timestamp;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
   
}
