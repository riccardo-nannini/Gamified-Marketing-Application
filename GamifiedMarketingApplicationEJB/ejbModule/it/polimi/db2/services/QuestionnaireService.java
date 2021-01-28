package it.polimi.db2.services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import it.polimi.db2.entities.Product;
import it.polimi.db2.entities.QuestionnaireAnswer;
import it.polimi.db2.exceptions.QuestionnaireAnswerException;
import it.polimi.db2.utilities.MarketingAnswers;

@Stateful
@LocalBean
public class QuestionnaireService {
	@PersistenceContext(unitName = "GamifiedMarketingApplicationEJB")
	private EntityManager em;
    
	private MarketingAnswers marketingAnswers = null;
	
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
    
    public void storeMarketingAnswersById(MarketingAnswers marketingAnswers) {
    	this.marketingAnswers = marketingAnswers;
    }
    
    public void cancelQuestionnaire() {
    	marketingAnswers = null;
    }
    
    public MarketingAnswers getPreviousAnswer() {
    	return marketingAnswers;
    }
    
    public void createQuestionnaireAnswer() {
    	//TODO    	
    }
    
    
    
}
