package it.polimi.db2.services;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import it.polimi.db2.entities.Product;
import it.polimi.db2.entities.Review;
import it.polimi.db2.entities.VariableQuestion;

@Stateless
public class ProductService {
	@PersistenceContext(unitName = "GamifiedMarketingApplicationEJB")
	private EntityManager em;

    public ProductService() {
    }
    
    public List<Product> findAllProducts() {
		List<Product> products = null;
		products = em.createNamedQuery("Product.findAll", Product.class).getResultList();
		return products;
	}
    
    //DOVREBBE RESISTITUIRE UN SOLO PRODOTTO (FARE CONTROLLI, ANCHE NEL MOMENTO IN CUI SE NE INSERISCE UNO NUOVO)
    //FORSE MEGLIO MANTENERE COSÌ, MAGARI UN GIORNO CI SARANNO PIÙ PRODOTTI PER DATA
    public List<Product> findProductsByDate(Date date) {
		List<Product> products = em
				.createNamedQuery("Product.findByDate", Product.class)
				.setParameter("date", date).getResultList();
		return products;
	}
    
    public void createProduct(String name, Date date, List<VariableQuestion> questions, byte[] img, List<Review> reviews) {
		
		Product product = new Product(name, date, img, questions, reviews);
		
		for(Review review: reviews) {
			review.setProdID(product);
		}
		
		for(VariableQuestion variableQuestion: questions) {
			variableQuestion.setProduct(product);
		}
		
		em.persist(product);
	}
    
    public void deleteProduct(int id) {
    	Product product = em.find(Product.class, id);
    	em.remove(product);
    }
    
    public Review createReview(String text) {
    	Review review = new Review(text);
    	return review;
    }
    
    public VariableQuestion createVariableQuestion(String text) {
    	VariableQuestion variableQuestion = new VariableQuestion(text);
    	return variableQuestion;
    }
    

}
