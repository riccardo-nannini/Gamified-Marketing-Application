package it.polimi.db2.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: VariableAnswer
 *
 */
@Entity
@Table(name = "variableanswer", schema = "gamified_db")
public class VariableAnswer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	public VariableAnswer() {
		super();
	}
   
}
