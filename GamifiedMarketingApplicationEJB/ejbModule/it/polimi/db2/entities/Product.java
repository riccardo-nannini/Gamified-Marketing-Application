package it.polimi.db2.entities;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "product", schema = "gamified_db")

@NamedQueries({ 
	@NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
	@NamedQuery(name = "Product.findByDate", query = "Select p FROM Product p WHERE p.date = :date"),
	@NamedQuery(name = "Product.findLeaderboardByProduct", 
	query = "SELECT u.username, q.points FROM QuestionnaireAnswer q JOIN User u "
			+ "WHERE q.product.id = :prodId and q.user.id = u.id "
			+ "ORDER BY q.points DESC")
})
public class Product implements Serializable {

	
	private static final long serialVersionUID = 1L;
	

	public Product() {
		super();
	}
	
	public Product(String name, Date date, byte[] image) {
		this.name = name;
		this.date = date;
		this.image = image;
	}
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	@Temporal(TemporalType.DATE)
	private Date date;
	
	@Basic(fetch = FetchType.LAZY)
	@Lob
	private byte[] image;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "prodID", cascade = CascadeType.ALL)
	private List<Review> reviews;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "product", cascade = CascadeType.ALL)
	private List<VariableQuestion> variableQuestions;


	public List<VariableQuestion> getVariableQuestions() {
		return variableQuestions;
	}


	public void setVariableQuestions(List<VariableQuestion> variableQuestions) {
		this.variableQuestions = variableQuestions;
	}


	public List<Review> getReviews() {
		return reviews;
	}


	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public byte[] getImage() {
		return image;
	}


	public void setImage(byte[] photoimage) {
		this.image = photoimage;
	}
	
	public String getImageData() {
		return Base64.getMimeEncoder().encodeToString(image);
	}
	

   
}
