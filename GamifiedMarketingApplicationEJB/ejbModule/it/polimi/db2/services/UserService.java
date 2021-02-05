package it.polimi.db2.services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import it.polimi.db2.entities.Product;
import it.polimi.db2.entities.QuestionnaireAnswer;
import it.polimi.db2.entities.User;
import it.polimi.db2.exceptions.CredentialsException;
import it.polimi.db2.exceptions.UsernamesException;

@Stateless
public class UserService {
	@PersistenceContext(unitName = "GamifiedMarketingApplicationEJB")
	private EntityManager em;

    
    public UserService() {
    
    }
        
	public User checkCredentials(String username, String password) throws CredentialsException {
		List<User> uList = null;
		try {
			uList = em.createNamedQuery("User.checkCredentials", User.class).setParameter(1, username).setParameter(2, password)
					.getResultList();
		} catch (PersistenceException e) {
			throw new CredentialsException("Could not verify credentals");
		}
		if (uList.isEmpty())
			return null;
		else if (uList.size() == 1)
			return uList.get(0);
		throw new CredentialsException("More than one user registered with same credentials");
	}
	
	public List<String> findAllUsernames() throws UsernamesException {
		List<String> usernames;
		try {
			usernames = em.createQuery("SELECT u.username FROM User u").getResultList();
		} catch (PersistenceException e) {
			throw new UsernamesException("Cound not retrieve the usernames");
		}
		return usernames;
	}
	
	public User registerUser(String username, String password, String email) {
		User user = new User(username, password, email);
		em.persist(user);
		return user;
	}
	
	public void blockUser(User user) {
		user.setBlocked(true);
		em.merge(user);
	}
	
	public boolean hasAlreadyDoneSurvey(Product product, int userID) {
		User managedUser = em.find(User.class, userID);
		em.refresh(managedUser);
		List<QuestionnaireAnswer> answers = managedUser.getAnswers();
		for (QuestionnaireAnswer ans: answers) {
			if (ans.getProduct().getId() == product.getId()) return true;
		}
		return false;
	}
	
}


