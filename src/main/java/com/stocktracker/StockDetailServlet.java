package com.stocktracker;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet implementation class StockDetailServlet
 */
@WebServlet("/stockdetailservlet")
public class StockDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 HttpSession session = request.getSession(false);
			
			if(session == null || session.getAttribute("user") == null) {
				response.sendRedirect("login.jsp");
				return;
			}
			

        String symbol = request.getParameter("symbol");
        
		StockDao stockDao = new StockDao();
		Stock stockdetail = stockDao.getStockBySymbol(symbol);
     
		if (stockdetail != null) {
			session.setAttribute("stockDetail", stockdetail);
			response.sendRedirect("stockdetails.jsp");
        } else {
            // Handle the case where the stock is not found
        	 response.sendRedirect("allstock.jsp");
        }     
	
	 	
	 
	 		
	  
	 	
	}

}
