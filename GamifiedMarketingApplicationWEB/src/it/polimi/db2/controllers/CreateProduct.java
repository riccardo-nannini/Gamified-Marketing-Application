package it.polimi.db2.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
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
import it.polimi.utils.ImageUtils;

import javax.servlet.http.Part;

@WebServlet("/CreateProduct")
@MultipartConfig
public class CreateProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "it.polimi.db2.services/ProductService")
	private ProductService productService;

	
	public void init() throws ServletException {
		ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
	}
	
    public CreateProduct() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = null;
		String date = null;
		int numberOfVariableQuestions = 0;
		byte[] imgByteArray;
		
		name = StringEscapeUtils.escapeJava(request.getParameter("name"));
		date = request.getParameter("date");
		Part imgFile = request.getPart("picture");
		InputStream imgContent = imgFile.getInputStream();
		imgByteArray = ImageUtils.readImage(imgContent);
		numberOfVariableQuestions = Integer.parseInt(request.getParameter("variableQuestionsNumber"));
		
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		String path = "";
		
		Product createdProduct = null;		
		
		try {
			
			if (productService.findProductsByDate(new SimpleDateFormat("yyyy-MM-dd").parse(date)).size() == 0) {
				//per la data scelta non sono ancora stati creati prodotti
				createdProduct = productService.createProduct(name, date, imgByteArray);
				ctx.setVariable("numberOfVariableQuestions", numberOfVariableQuestions);
				ctx.setVariable("createdProduct", createdProduct.getId());
				path = "/WEB-INF/CreateVariableQuestions.html";
				templateEngine.process(path, ctx, response.getWriter());
			} else {
				request.setAttribute("error", "error");
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/GoToCreationPage");
				dispatcher.forward(request, response);
			}
		} catch (ParseException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Problem with product creation");
			e.printStackTrace();
		}
		

	
	}

}
