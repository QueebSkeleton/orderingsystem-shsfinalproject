package web.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Country;
import dao.CountryDAO;

@WebServlet("/SetCountry.do")
public class CountrySetServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
    public CountrySetServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	long id = Long.parseLong(request.getParameter("id"));
    	
    	CountryDAO countryDao = new CountryDAO();
    	Country country = countryDao.getByID(id);
    	
    	HttpSession session = request.getSession();
    	session.setAttribute("countrySet", country);
    	
    	response.sendRedirect("index.jsp");
    }
    
}
