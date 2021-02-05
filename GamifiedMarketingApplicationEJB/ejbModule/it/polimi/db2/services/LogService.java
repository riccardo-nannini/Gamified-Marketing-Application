package it.polimi.db2.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import it.polimi.db2.entities.Log;
import it.polimi.db2.exceptions.LogException;

@Stateless
public class LogService {
	@PersistenceContext(unitName = "GamifiedMarketingApplicationEJB")
	private EntityManager em;
    
    public LogService() {
    	
    }
    
    public List<Log> findAll() throws LogException {
    	List<Log> logs = null; 
    	try {
    		logs = em.createNamedQuery("Log.findAll", Log.class)
    				.setMaxResults(20).getResultList();
    		} catch (PersistenceException e) {
				throw new LogException("Could not retrieve logs");
			}
    	if (logs.isEmpty()) return null;
    	return logs;    	
    }

}
