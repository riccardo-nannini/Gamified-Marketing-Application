package it.polimi.db2.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;


import it.polimi.db2.entities.Product;
import it.polimi.db2.entities.Review;
import it.polimi.db2.entities.VariableQuestion;
import it.polimi.db2.exceptions.LeaderboardException;
import it.polimi.db2.exceptions.QuestionnaireAnswerException;

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
    
    public Product createProduct(String name, String date, byte[] img) throws ParseException {
		Product product = new Product(name, new SimpleDateFormat("yyyy-MM-dd").parse(date), img);
		em.persist(product);
		return product;
	}
    
    public void deleteProduct(int id) {
    	Product product = em.find(Product.class, id);
    	em.remove(product);
    }
    
    public Review createReview(String text) {
    	Review review = new Review(text);
    	return review;
    }
    
    
    public Product findProductById(int prodId) {
    	return(em.find(Product.class, prodId));
    }
    
    public List<Product> findPastProducts(Date date) {
		List<Product> products = em
				.createNamedQuery("Product.findPast", Product.class)
				.setParameter("date", date).getResultList();
		return products;
	}

}
