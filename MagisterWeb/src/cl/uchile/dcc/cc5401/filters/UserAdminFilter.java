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

import cl.uchile.dcc.cc5401.model.dto.UserDTO;

/**
 * Servlet Filter implementation class UserAdminFilter
 */
@WebFilter("/app/admin/userAdmin/*")
public class UserAdminFilter implements Filter {
	private static String ERROR_PERMISOS = "/app/permisos.jsp";
    
	public UserAdminFilter() {
        
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		 HttpServletRequest request = (HttpServletRequest) req;
         HttpServletResponse response = (HttpServletResponse) res;
         HttpSession session = request.getSession(true);
         
         if(session.getAttribute("user")!=null){
        	 UserDTO user = (UserDTO) session.getAttribute("user");
        	//Si el usuario tiene permisos para administrar usuarios, se le deja pasar, si no se redirige a una página de error.
        	 if(user.hasPermisos("ADMIN_USERS"))
        		 chain.doFilter(request, response);
        	 else{
        		 response.sendRedirect(request.getContextPath() + ERROR_PERMISOS);
        	 }
         }
         else{
        	 response.sendRedirect(request.getContextPath() + ERROR_PERMISOS); 
         }
	}

	public void init(FilterConfig fConfig) throws ServletException {
	
	}

}
