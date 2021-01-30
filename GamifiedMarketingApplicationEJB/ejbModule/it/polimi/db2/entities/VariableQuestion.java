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
	
	//BASTA?
	public VariableQuestion(String text) {
		this.text = text;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "prodID")
	private Product product;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "variableanswer", schema = "gamified_db", joinColumns = @JoinColumn(name = "variableQuestionID"))
	@MapKeyJoinColumn(name = "answerID")
	@Column(name = "answer")
	private Map<QuestionnaireAnswer, String> variableAnswer;
	
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

	public Map<QuestionnaireAnswer, String> getVariableAnswer() {
		return variableAnswer;
	}

	public void setVariableAnswer(QuestionnaireAnswer q, String answer) {
		variableAnswer.put(q, answer);
	}

	public void removeQuestionnaireAnswer(QuestionnaireAnswer q) {
		variableAnswer.remove(q);
	}

	
   
}
