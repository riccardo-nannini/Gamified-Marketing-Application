package it.polimi.db2.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Email;

import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;


import it.polimi.db2.entities.Product;
import it.polimi.db2.entities.QuestionnaireAnswer;
import it.polimi.db2.entities.VariableQuestion;
import it.polimi.db2.exceptions.QuestionnaireAnswerException;
import it.polimi.db2.services.ProductService;
import it.polimi.db2.services.QuestionnaireService;


@WebServlet("/GoToQuestionnaireInfo")
public class GoToQuestionnaireInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private TemplateEngine templateEngine;

	@EJB(name = "it.polimi.db2.services/ProductService")
	private ProductService productService;
	@EJB(name = "it.polimi.db2.services/QuestionnaireService")
	private QuestionnaireService questionnaireService;
	

	public void init() throws ServletException {
		ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
	}
  
	
    public GoToQuestionnaireInfo() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		int prodId;
		try {
			prodId = Integer.decode(request.getParameter("prod"));
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing value");
			return;
		}
		
		Product product = productService.findProductById(prodId);
		if (product == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Incorrect param value");
			return;
		}
		List<QuestionnaireAnswer> answers = null;
		List<QuestionnaireAnswer> deleted = null;
		try {
			answers = questionnaireService.findQuestionnaireByProduct(product);
			deleted = questionnaireService.findQuestionnaireByProductDeleted(product);
		} catch (QuestionnaireAnswerException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Cannot retrieve answer");
			return;
		}
		
		Set<VariableQuestion> variableQuestion = null;
		if (answers != null)  variableQuestion = answers.get(0).getVariableAnswer().keySet();

		
		String path = "/WEB-INF/questionnaireInfo.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("product", product);
		ctx.setVariable("answers", answers);
		ctx.setVariable("questions", variableQuestion);
		ctx.setVariable("deleted", deleted);
		templateEngine.process(path, ctx, response.getWriter());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
