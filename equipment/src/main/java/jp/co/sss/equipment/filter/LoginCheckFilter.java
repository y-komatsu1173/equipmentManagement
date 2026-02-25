package jp.co.sss.equipment.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * ログインフィルター
 */
@WebFilter("/*")
public class LoginCheckFilter extends HttpFilter {

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
	        throws IOException, ServletException {

	    String path = request.getRequestURI();

	    //ログインページを外す
	    if (path.equals("/") 
	        || path.equals("/login") 
	        || path.startsWith("/css/")
	        || path.startsWith("/js/")
	        || path.startsWith("/images/")) {

	        chain.doFilter(request, response);
	        return;
	    }

	    HttpSession session = request.getSession(false);

	    if (session == null || session.getAttribute("user") == null) {
	        response.sendRedirect("/");
	        return;
	    }

	    chain.doFilter(request, response);
	}
}