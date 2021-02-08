package it.polimi.db2.entities;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.*;

@Entity
@Table(name="variablequestion", schema = "gamified_db")
public class VariableQuestion implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public VariableQuestion() {
		super();
	}
	
	public VariableQuestion(String text, Product product) {
		this.text = text;
		this.product = product;
	}
	
	public VariableQuestion(String text) {
		this.text = text;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "prodID")
	private Product product;
	
	private String text;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
   
}
