package it.polimi.db2.controllers;

import java.io.IOException;
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

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.db2.entities.Product;
import it.polimi.db2.entities.QuestionnaireAnswer;
import it.polimi.db2.exceptions.QuestionnaireAnswerException;
import it.polimi.db2.services.ProductService;
import it.polimi.db2.services.QuestionnaireService;


@WebServlet("/GoToLeaderboardPage")
public class GoToLeaderboardPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "it.polimi.db2.services/ProductService")
	private ProductService productService;
	
    public GoToLeaderboardPage() {
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

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Product productOfTheDay = productService.findProductsByDate(new Date()).get(0);
		
		QuestionnaireService questionnaireService = (QuestionnaireService) request.getSession().getAttribute("QuestionBean");
		if(questionnaireService == null) {
			try {
                InitialContext ic = new InitialContext();
                questionnaireService = (QuestionnaireService) 
                        ic.lookup("java:global/GamifiedMarketingApplicationWEB/QuestionnaireService!it.polimi.db2.services.QuestionnaireService");
                request.getSession().setAttribute("QuestionBean", questionnaireService);
                System.out.println("stateful bean created");
              } catch (NamingException e) {
                throw new ServletException(e);
              }
		}
		
		
		List<Object[]> results = null;
		
		
		try {
			results = questionnaireService.findLeaderbordByProduct(productOfTheDay);
		} catch (QuestionnaireAnswerException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			e.printStackTrace();
		}
		
		String path = "/WEB-INF/Leaderboard.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("product", productOfTheDay);
		ctx.setVariable("results", results);
		templateEngine.process(path, ctx, response.getWriter());
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
