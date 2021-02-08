package it.polimi.db2.services;

import java.sql.Timestamp;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import it.polimi.db2.entities.Log;
import it.polimi.db2.entities.Product;
import it.polimi.db2.entities.User;
import it.polimi.db2.exceptions.LeaderboardException;
import it.polimi.db2.exceptions.LogException;

@Stateless
public class LogService {
	@PersistenceContext(unitName = "GamifiedMarketingApplicationEJB")
	private EntityManager em;
    
    public LogService() {
    	
    }
    
    
    public void createLog(Timestamp timestamp, User user) {
    	Log log = new Log(timestamp,user);
    	em.persist(log);
    }
    
    //Retrieve all the logs timestamp with its user
	public List<Object[]> findUserLogs() throws LogException {
		List<Object[]> results;
		try {
			TypedQuery<Object[]> query = em.createNamedQuery("Log.findUserLogs", Object[].class);
			results = query.getResultList();
		} catch (PersistenceException e) {
			throw new LogException("Could not retrieve logs");
		}
		return results;
	}

}
