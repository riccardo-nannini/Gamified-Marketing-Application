package it.polimi.db2.entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "review", schema = "gamified_db")
public class Review implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public Review() {
		super();
	}
	
	
	public Review(String text) {
		this.text = text;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String text;
	
	@ManyToOne
	@JoinColumn(name = "prodID")
	private Product prodID;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Product getProdID() {
		return prodID;
	}

	public void setProdID(Product prodID) {
		this.prodID = prodID;
	}
	
	
	
   
}
