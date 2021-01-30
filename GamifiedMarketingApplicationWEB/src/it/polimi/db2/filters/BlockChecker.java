package it.polimi.db2.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.polimi.db2.entities.User;

public class BlockChecker implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("Eseguendo filtro block");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String path = req.getServletContext().getContextPath() + "/blocked.html";

		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");

		if (user.getBlocked()) {
			res.sendRedirect(path);
			return;
		}
		chain.doFilter(request, response);
	}

}
