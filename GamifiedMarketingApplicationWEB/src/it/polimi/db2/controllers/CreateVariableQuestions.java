package it.polimi.db2.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.db2.entities.Product;
import it.polimi.db2.services.ProductService;
import it.polimi.db2.services.VariableQuestionService;
import it.polimi.utils.ImageUtils;

import javax.servlet.http.Part;

@WebServlet("/CreateVariableQuestions")
@MultipartConfig
public class CreateVariableQuestions extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "it.polimi.db2.services/VariableQuestionService")
	private VariableQuestionService variableQuestionService;

	
	public void init() throws ServletException {
		ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
	}
	
    public CreateVariableQuestions() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int productId = Integer.parseInt(request.getParameter("createdProduct"));
		int numberOfVariableQuestions = Integer.parseInt(request.getParameter("numberOfVariableQuestions"));
		
		String questionText;
		for (int i = 1; i<=numberOfVariableQuestions; i++) {
			questionText = request.getParameter(Integer.toString(i)); 
			variableQuestionService.CreateVariableQuestion(questionText, productId);
			
		}
		
		
		String path = "/WEB-INF/CreateProductGreetings.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		templateEngine.process(path, ctx, response.getWriter());
		
		
		//new SimpleDateFormat("yyyy-MM-dd").parse(

	
	}

}
