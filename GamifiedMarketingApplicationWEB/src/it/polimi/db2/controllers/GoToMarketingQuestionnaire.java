package it.polimi.db2.controllers;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
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
import it.polimi.db2.entities.User;
import it.polimi.db2.services.ProductService;
import it.polimi.db2.services.QuestionnaireFillingService;
import it.polimi.db2.services.UserService;


@WebServlet("/GoToMarketing")
public class GoToMarketingQuestionnaire extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "it.polimi.db2.services/ProductService")
	private ProductService productService;
	@EJB(name = "it.polimi.db2.services/UserService")
	private UserService userService;
	
	public GoToMarketingQuestionnaire() {
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
		
		
		if (userService.hasAlreadyDoneSurvey(productOfTheDay, user.getId())) {
			request.setAttribute("message", "questionnaireJustCompiled");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/GoToHomePage");
			dispatcher.forward(request, response);
			return;
		}
		
		QuestionnaireFillingService questionnaireFillingService = (QuestionnaireFillingService) request.getSession().getAttribute("QuestionBean");
		
		if(questionnaireFillingService == null) {
			try {
                InitialContext ic = new InitialContext();
                 
                questionnaireFillingService = (QuestionnaireFillingService) 
                        ic.lookup("java:global/GamifiedMarketingApplicationWEB/QuestionnaireFillingService!it.polimi.db2.services.QuestionnaireFillingService");
                request.getSession().setAttribute("QuestionBean", questionnaireFillingService);
  
              } catch (NamingException e) {
                throw new ServletException(e);
              }
		}
		
		
		List<String> previousAnswer = questionnaireFillingService.getPreviousAnswer();
		
		if (previousAnswer == null) {
			previousAnswer = new ArrayList<>();
			for (int i = 0; i < productOfTheDay.getVariableQuestions().size(); i++) {
				previousAnswer.add("");
			}
		}
		
		String path = "/WEB-INF/Marketing.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("product", productOfTheDay);
		ctx.setVariable("previousAnswers", previousAnswer);
		templateEngine.process(path, ctx, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void destroy() {
	}

}
