package it.polimi.db2.controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.ejb.EJB;
import javax.security.auth.login.CredentialException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.db2.entities.User;
import it.polimi.db2.exceptions.CredentialsException;
import it.polimi.db2.services.UserService;

@WebServlet("/RegisterUser")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "it.polimi.db2.services/UserService")
	private UserService userService;

	public Register() {
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = null;
		String password = null;
		String repeat_password = null;
		String email = null;

		username = request.getParameter("username");
		password = request.getParameter("pwd");
		repeat_password = request.getParameter("repeatpwd");
		email = request.getParameter("email");

		User user = null;
		
		String path;
		
		String errorMessage = null;
		
		if (!password.contentEquals(repeat_password)) {
			errorMessage = "Mismatching between the fields password and repeat password";
		} else {
			if (!checkEmailValidity(email)) {
				errorMessage = "Email address is not well formatted";
			} else {
				try {
					user = userService.checkCredentials(username, password);
				} catch (CredentialsException e) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					e.printStackTrace();
					return;
				}

				if (user != null) {
					errorMessage = "You already have an account, please log in.";
				} else {
					user = userService.registerUser(username, password, email);
					request.getSession().setAttribute("user", user);
					path = getServletContext().getContextPath() + "/GoToHomePage";
					response.sendRedirect(path);
				}
			}
		}
		
		//se c'è stato qualche errore torno all'index 
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("errorMsgRegister", errorMessage);
		path = "/index.html";
		templateEngine.process(path, ctx, response.getWriter());
	}


	private boolean checkEmailValidity(String email) {
		String[] splitted = email.split("@");
		if (splitted.length != 2)
			return false;
		if (splitted[1].split("\\.").length < 2)
			return false; // at least something.domain
		return true;
	}

	public void destroy() {
	}

}
