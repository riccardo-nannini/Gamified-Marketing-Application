package it.polimi.db2.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.db2.entities.Product;
import it.polimi.db2.entities.VariableQuestion;

@Stateless
public class VariableQuestionService {
	@PersistenceContext(unitName = "GamifiedMarketingApplicationEJB")
	private EntityManager em;
    
	
    public VariableQuestionService() {
    	
    }
    
    public void CreateVariableQuestion(String text, int productId) {
    	Product product = em.find(Product.class, productId);
    	VariableQuestion variableQuestion = new VariableQuestion(text, product);
    	product.addVariableQuestions(variableQuestion);
    	em.persist(product);
    }

}
