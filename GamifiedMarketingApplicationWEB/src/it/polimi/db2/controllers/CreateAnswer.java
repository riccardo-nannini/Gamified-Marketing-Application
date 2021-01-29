package it.polimi.db2.controllers;

import java.io.IOException;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;

import it.polimi.db2.entities.Product;
import it.polimi.db2.entities.User;
import it.polimi.db2.services.ProductService;
import it.polimi.db2.services.QuestionnaireService;


@WebServlet("/CreateAnswer")
public class CreateAnswer extends HttpServlet {
	private static final long serialVersionUID = 1L;	
	@EJB(name = "it.polimi.db2.services/ProductService")
	private ProductService productService;
	
       
    public CreateAnswer() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
		QuestionnaireService questionnaireService = (QuestionnaireService) request.getSession().getAttribute("QuestionBean");
		
		if (questionnaireService == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		Product productOfTheDay = productService.findProductsByDate(new Date()).get(0);
		if (productOfTheDay == null) response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Cannot find the product of the day");

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		int answer1;
		String answer2;
		String answer3;
		
		//if the age is not specified it is set to 0
		try {
			answer1 = Integer.parseInt(request.getParameter("answ1"));
		} catch (Exception e) {
			answer1 = 0;
		}
		
		//TODO in teoria uno potrebbe non specificare questi dati e salvare comunque il questionario di marketing da specifica
		//quindi non � corretto lanciare eccezione
		try {
			answer2 = StringEscapeUtils.escapeJava(request.getParameter("answ2"));
			answer3 = StringEscapeUtils.escapeJava(request.getParameter("answ3"));
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Incorrect params");
			return;		
		}
		
		questionnaireService.createQuestionnaireAnswer(answer1, answer2, answer3, user, productOfTheDay);

		//Remove the Stateful bean from session since it's been deleted by the container after the previous method
		session.removeAttribute("QuestionBean");
		
		String path = getServletContext().getContextPath() + "/GoToGreetingsPage";
		response.sendRedirect(path);
	}

}
