package it.polimi.db2.services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import it.polimi.db2.entities.Product;
import it.polimi.db2.entities.QuestionnaireAnswer;
import it.polimi.db2.exceptions.LeaderboardException;
import it.polimi.db2.exceptions.QuestionnaireAnswerException;

@Stateless
@LocalBean
public class QuestionnaireService {
	@PersistenceContext(unitName = "GamifiedMarketingApplicationEJB")
	private EntityManager em;
    
    public QuestionnaireService() {
    	
    }
    
    public List<QuestionnaireAnswer> findQuestionnaireByProduct(Product product) throws QuestionnaireAnswerException {
    	List<QuestionnaireAnswer> answers = null;
    	try {
    		answers = em.createNamedQuery("QuestionnaireAnswer.findByProduct", QuestionnaireAnswer.class)
    				.setParameter("prodId", product.getId()).getResultList();
    	} catch (PersistenceException e) {
    		throw new QuestionnaireAnswerException("Could not retrieve questionnaire answers related to the product");
		}
    	if (answers.isEmpty()) return null;
    	return answers;
    }
    
    public List<QuestionnaireAnswer> findQuestionnaireByProductDeleted(Product product) throws QuestionnaireAnswerException {
    	List<QuestionnaireAnswer> answers = null;
    	try {
    		answers = em.createNamedQuery("QuestionnaireAnswer.findByProductDeleted", QuestionnaireAnswer.class)
    				.setParameter("prodId", product.getId()).getResultList();
    	} catch (PersistenceException e) {
    		throw new QuestionnaireAnswerException("Could not retrieve questionnaire answers related to the product");
		}
    	if (answers.isEmpty()) return null;
    	return answers;
    }
    
    public List<Object[]> findLeaderbordByProduct(Product product) throws LeaderboardException {
    	List<Object[]> results;
    	try {    		
    		TypedQuery<Object[]> query = em.createNamedQuery("QuestionnaireAnswer.findLeaderboardByProduct", Object[].class).setParameter("prodId", product.getId());
    		results = query.getResultList();
    	} catch (PersistenceException e) {
    		throw new LeaderboardException("Could not retrieve questionnaire answers related to the product");
		}
    	return results;
    }
    

}
