package it.polimi.db2.entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "offensive_word", schema = "gamified_db")
@NamedQuery(name = "OffensiveWord.findAll", query = "SELECT w FROM OffensiveWord w")
public class OffensiveWord implements Serializable {	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String word;
	
	public OffensiveWord() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
   
}
