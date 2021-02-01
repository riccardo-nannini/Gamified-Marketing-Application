package it.polimi.db2.entities;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.*;

@Entity
@Table(name = "questionnarieanswer", schema = "gamified_db")
@NamedQuery(name = "QuestionnaireAnswer.findByProduct",
	query = "SELECT q FROM QuestionnaireAnswer q WHERE q.product.id = :prodId and q.deleted = FALSE")
@NamedQuery(name = "QuestionnaireAnswer.findLeaderboardByProduct", 
query = "SELECT u.username, q.points FROM QuestionnaireAnswer q JOIN User u "
		+ "WHERE q.product.id = :prodId and q.user.id = u.id "
		+ "ORDER BY q.points DESC")
@NamedQuery(name = "QuestionnaireAnswer.findByProductDeleted",
query = "SELECT q FROM QuestionnaireAnswer q WHERE q.product.id = :prodId and q.deleted = TRUE")
public class QuestionnaireAnswer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int answ1;
	
	private String answ2;
	
	private String answ3;
	
	private boolean deleted;
	
	private int points;
	
	@ManyToOne
	@JoinColumn(name = "userID")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "prodID")
	private Product product;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "variableanswer", schema = "gamified_db", joinColumns = @JoinColumn(name = "answerID"))
	@MapKeyJoinColumn(name = "variableQuestionID")
	@Column(name = "answer")
	private Map<VariableQuestion, String> variableAnswer;

	public QuestionnaireAnswer() {
		super();
	}
	
	public QuestionnaireAnswer (int answ1, String answ2, String answ3, Boolean deleted, User user, Product product, Map<VariableQuestion, String> variableAnswer) {
		this.answ1 = answ1;
		this.answ2 = answ2;
		this.answ3 = answ3;
		this.deleted = deleted;
		this.user = user;
		this.product = product;
		this.variableAnswer = variableAnswer;
		this.points = 0;
	}

	public QuestionnaireAnswer(Boolean deleted, User user, Product product) {
		this.deleted = deleted;
		this.user = user;
		this.product = product;
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
	
	public Map<VariableQuestion, String> getVariableAnswer() {	
		return variableAnswer;
	}

	public void setVariableAnswer(VariableQuestion v, String answer) {
		variableAnswer.put(v, answer);
	}

	public void removeVariableAnswer(VariableQuestion v) {
		variableAnswer.remove(v);
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
   
}
