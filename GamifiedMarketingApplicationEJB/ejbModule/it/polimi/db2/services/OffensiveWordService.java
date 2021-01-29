package it.polimi.db2.services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import it.polimi.db2.entities.OffensiveWord;
import it.polimi.db2.exceptions.OffensiveWordException;


@Stateless
@LocalBean
public class OffensiveWordService {
	@PersistenceContext(unitName = "GamifiedMarketingApplicationEJB")
	private EntityManager em;

    public OffensiveWordService() {
    	
    }
    
    //true iff one of the strings of answers contains an offensive word
    public boolean checkOffensiveWords(List<String> answers) throws OffensiveWordException {
    	List<OffensiveWord> offensiveWords = null;
    	try {
    		offensiveWords = em.createNamedQuery("OffensiveWord.findAll", OffensiveWord.class)
    				.getResultList();
    	} catch (PersistenceException e) {
    		throw new OffensiveWordException("Can't retrieve offensive words");
    	}
    	
    	if (offensiveWords == null) return false; 
    	for (String text: answers) {
    		for (OffensiveWord offWord: offensiveWords) {
    			if (text.contains(offWord.getWord())) return true;
    		}
    	}
    	return false;
    }

}
