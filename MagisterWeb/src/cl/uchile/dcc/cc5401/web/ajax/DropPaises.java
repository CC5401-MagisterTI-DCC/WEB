package cl.uchile.dcc.cc5401.web.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cl.uchile.dcc.cc5401.model.dao.PaisDAO;
import cl.uchile.dcc.cc5401.model.dao.impl.factory.PaisDAOFactory;
import cl.uchile.dcc.cc5401.model.dto.PaisDTO;

@WebServlet("/DropPaises")
public class DropPaises extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PaisDAO paisDAO;
       
    public DropPaises() {
        super();
        paisDAO = PaisDAOFactory.getPaisDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[INFO] DropPaises : Get");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		 
		 try{
			 
			 List<PaisDTO> paises = paisDAO.getAll();
			 
			 if(paises!=null){
				 for(PaisDTO p : paises){
					 out.print("<option value='"+p.getId()+"'>"+p.getNombre()+"</option>");
				 }
			 }else{
				 out.print("No existen paises!");
			 }
		 }
		 catch (Exception e){
			 e.printStackTrace();
		 }
		 finally{
			 out.close();
		 }
	}


}
