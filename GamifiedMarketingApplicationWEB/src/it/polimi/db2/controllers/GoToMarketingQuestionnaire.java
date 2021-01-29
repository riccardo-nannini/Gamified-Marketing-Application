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
import it.polimi.db2.services.ProductService;
import it.polimi.db2.services.QuestionnaireService;
import it.polimi.db2.services.UserService;
import it.polimi.db2.utilities.MarketingAnswers;



@WebServlet("/GoToMarketing")
public class GoToMarketingQuestionnaire extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "it.polimi.db2.services/ProductService")
	private ProductService productService;
	
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
		
		//FILTRO PER USER		
		
		QuestionnaireService questionnaireService = (QuestionnaireService) request.getSession().getAttribute("QuestionBean");
		if(questionnaireService == null) {
			try {
                InitialContext ic = new InitialContext();
                questionnaireService = new QuestionnaireService();
 
                request.getSession().setAttribute("QuestionBean", questionnaireService);
 
                System.out.println("stateful bean created");
 
              } catch (NamingException e) {
                throw new ServletException(e);
              }
		}
		
		
		Product productOfTheDay = productService.findProductsByDate(new Date()).get(0);
		List<String> previousAnswer = questionnaireService.getPreviousAnswer();
		
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
