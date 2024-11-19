package com.stocktracker;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet implementation class AddToMyWatchListServlrt
 */

@WebServlet("/addstockservlet")
public class AddToMyWatchListServlrt extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 HttpSession session = request.getSession(false);
			
			if(session == null || session.getAttribute("user") == null) {
				response.sendRedirect("login.jsp");
				return;
			}
			


		String symbol = request.getParameter("symbol");
        User user = (User ) session.getAttribute("user");
        WatchlistDao watchlistDao = new WatchlistDao();

        try {
            if (!watchlistDao.isStockInWatchlist(user.getUsername(),symbol)) {
                watchlistDao.addToWatchList(user.getUsername(), symbol);
                

                // Add the updated watchlist to the session
                Watchlist watchlist = watchlistDao.getWatchList(user.getUsername());
                session.setAttribute("watchlist", watchlist);
                
                response.sendRedirect("mystocks.jsp");
            } else {
                response.sendRedirect("mystocks.jsp?error=stock_already_in_watchlist");
            }
        } catch (Exception e) {
            response.sendRedirect("mystocks.jsp?error=error_adding_stock");
        }
	 	
	}

}
