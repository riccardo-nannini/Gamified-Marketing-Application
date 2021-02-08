package it.polimi.db2.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.*;


@Entity
@Table(name = "log", schema = "gamified_db")
@NamedQuery(name = "Log.findUserLogs", query = "SELECT l.timestamp, u.username FROM Log l JOIN User u "
										     + "WHERE l.user = u ORDER BY l.timestamp DESC")
public class Log implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	public Log(Timestamp timestamp, User user) {
		this.timestamp = timestamp;
		this.user = user;
	}

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
