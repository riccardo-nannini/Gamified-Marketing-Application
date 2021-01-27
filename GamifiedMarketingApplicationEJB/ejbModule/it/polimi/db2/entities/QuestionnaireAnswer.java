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
   
}
