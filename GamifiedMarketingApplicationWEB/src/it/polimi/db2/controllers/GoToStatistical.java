package it.polimi.db2.controllers;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.db2.entities.Product;
import it.polimi.db2.entities.Review;
import it.polimi.db2.entities.User;
import it.polimi.db2.entities.VariableQuestion;
import it.polimi.db2.exceptions.OffensiveWordException;
import it.polimi.db2.services.OffensiveWordService;
import it.polimi.db2.services.ProductService;
import it.polimi.db2.services.QuestionnaireFillingService;
import it.polimi.db2.services.UserService;

@WebServlet("/GoToStatistical")
public class GoToStatistical extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "it.polimi.db2.services/ProductService")
	private ProductService productService;
	
	@EJB(name = "it.polimi.db2.services/OffensiveWordService")
	private OffensiveWordService offensiveWordService;
	
	@EJB(name = "it.polimi.db2.services/UserService")
	private UserService userService;
	
	public GoToStatistical() {
		super();
	}

	public void init() throws ServletException {
		ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				
		Product productOfTheDay = productService.findProductsByDate(new Date()).get(0);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		QuestionnaireFillingService questionnaireFillingService = (QuestionnaireFillingService) request.getSession().getAttribute("QuestionBean");
		if (questionnaireFillingService == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		List<String> marketingAnswers = new ArrayList<String>();
		for (int i = 0; i < productOfTheDay.getVariableQuestions().size(); i++) {
			marketingAnswers.add(request.getParameter(Integer.toString(i)));
		}
		try {
			if (!offensiveWordService.checkOffensiveWords(marketingAnswers)) {
				questionnaireFillingService.storeMarketingAnswers(marketingAnswers);
			} else {
				questionnaireFillingService.destroy();
				userService.blockUser(user);
				String path = getServletContext().getContextPath() + "/blocked.html";
				response.sendRedirect(path);
			}
			
		} catch (OffensiveWordException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
	//TODO credo manchi controllo che arrivino effettivamente tutte le risposte
		
		String path = "/WEB-INF/Statistical.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		templateEngine.process(path, ctx, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void destroy() {
	}

}
