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
 * Servlet Filter implementation class SkipFilter
 */
@WebFilter("/app/login")
public class SkipFilter implements Filter {

	private static String POSTULACIONES_PAGE = "/app/admin/postulaciones";
	
    public SkipFilter() {
    	
    }

    public void init(FilterConfig fConfig) throws ServletException {
    	
	}
    
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		 HttpServletRequest request = (HttpServletRequest) req;
         HttpServletResponse response = (HttpServletResponse) res;
         HttpSession session = request.getSession(true);
         
         //Si no se encuentra logeado, que siga al login, si no, se redirige automáticamente a la bandeja del usuario (Página de postulaciones)
         if(session.getAttribute("user")==null){
        	 chain.doFilter(request, response);
         }
         else{
        	 response.sendRedirect(request.getContextPath() + POSTULACIONES_PAGE); 
         }
	}

	public void destroy() {
		
	}
	
}
