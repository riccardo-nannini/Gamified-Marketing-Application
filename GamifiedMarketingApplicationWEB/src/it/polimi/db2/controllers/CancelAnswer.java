package it.polimi.db2.controllers;

import java.io.IOException;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.polimi.db2.entities.Product;
import it.polimi.db2.entities.User;
import it.polimi.db2.services.ProductService;
import it.polimi.db2.services.QuestionnaireService;


@WebServlet("/CancelAnswer")
public class CancelAnswer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB(name = "it.polimi.db2.services/ProductService")
	private ProductService productService;
       
    public CancelAnswer() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		QuestionnaireService questionnaireService = (QuestionnaireService) request.getSession().getAttribute("QuestionBean");
				
		if (questionnaireService == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		Product productOfTheDay = productService.findProductsByDate(new Date()).get(0);
		if (productOfTheDay == null) response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Cannot find the product of the day");

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		questionnaireService.cancelQuestionnaire(user, productOfTheDay);
		
		//Remove the Stateful bean from session since it's been deleted by the container after the previous method
		session.removeAttribute("QuestionBean");
		
		String path = getServletContext().getContextPath() + "/GoToHomePage";
		response.sendRedirect(path);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
