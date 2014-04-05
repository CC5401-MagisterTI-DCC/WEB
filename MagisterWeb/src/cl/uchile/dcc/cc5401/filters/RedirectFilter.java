package cl.uchile.dcc.cc5401.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class RedirectFilter
 */
@WebFilter("/app/admin/*")
public class RedirectFilter implements Filter {

	private static final String ERROR_PERMISOS = "/app/permisos.jsp";

	public RedirectFilter() {

	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		HttpSession session = request.getSession(true);

		if (session.getAttribute("user") != null) {
			chain.doFilter(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + ERROR_PERMISOS);
		}
	}

	public void destroy() {

	}

}
