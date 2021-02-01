package it.polimi.db2.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import it.polimi.db2.entities.Product;
import it.polimi.db2.entities.QuestionnaireAnswer;
import it.polimi.db2.entities.User;
import it.polimi.db2.entities.VariableQuestion;
import it.polimi.db2.exceptions.QuestionnaireAnswerException;

@Stateful
public class QuestionnaireFillingService {
	@PersistenceContext(unitName = "GamifiedMarketingApplicationEJB")
	private EntityManager em;
    
	private List<String> marketingAnswers = null;
	
    public QuestionnaireFillingService() {
    	
    }
    
    public void storeMarketingAnswers(List<String> marketingAnswers) {
    	this.marketingAnswers = marketingAnswers;
    }
    
    @Remove
    public void cancelQuestionnaire(User user, Product product) {
    	
    	QuestionnaireAnswer q = new QuestionnaireAnswer(true, user, product);    	
    	em.persist(q);
    	
    	//Clean before deallocation
    	marketingAnswers = null;
    }
    
    public List<String> getPreviousAnswer() {
    	return marketingAnswers;
    }
    
    @Remove
    public void createQuestionnaireAnswer(int answ1, String answ2, String answ3, User user, Product product) {
    	
    	Map<VariableQuestion, String> map = new HashMap<>();
    	List<VariableQuestion> variableQuestions = product.getVariableQuestions();
    	for (int i=0; i<variableQuestions.size(); i++) {
    		map.put(variableQuestions.get(i), marketingAnswers.get(i));
    	}
    	QuestionnaireAnswer q = new QuestionnaireAnswer(answ1, answ2, answ3, false, user, product, map);    	
    	em.persist(q);
    	
    	//Clean before deallocation
    	marketingAnswers = null;

    }
  
    @Remove
    public void destroy() {
    	
    }
    
    
    
}
