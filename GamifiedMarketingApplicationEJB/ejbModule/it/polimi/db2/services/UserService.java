package it.polimi.db2.services;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.db2.entities.User;

/**
 * Session Bean implementation class UserService
 */
@Stateless
@LocalBean
public class UserService {
	@PersistenceContext(unitName = "GamifiedMarketingApplicationEJB")
	private EntityManager em;

    /**
     * Default constructor. 
     */
    public UserService() {
        // TODO Auto-generated constructor stub
    }
    
    
	public void createUser(String name) {
		User u = new User();
		u.setBlocked(false);
		u.setEmail("loizo");
		u.setPassword("esasperatne");
		u.setRole("mario");
		u.setUsername(name);
		em.persist(u);
		
	}
	
}


