package com.stocktracker;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet implementation class DeleteFromWatchListServlet
 */
@WebServlet("/removestockservlet")
public class DeleteFromWatchListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		  HttpSession session = request.getSession(false);
		  if (session == null || session.getAttribute("user") == null) {
	            response.sendRedirect("login.jsp");
	            return;
	        }
		  
		  			String symbol = request.getParameter("symbol");
		  			User user = (User ) session.getAttribute("user");
	
			        WatchlistDao watchlistDao = new WatchlistDao();
			        boolean removed = watchlistDao.removeFromWatchList(user.getUsername(), symbol);
			        
			        Watchlist mystocklist = watchlistDao.getWatchList(user.getUsername());
			        session.setAttribute("watchlist", mystocklist);
			        

			        // Add the updated watchlist to the session
			        Watchlist watchlist = watchlistDao.getWatchList(user.getUsername());
			        session.setAttribute("watchlist", watchlist);

			        // Redirect with feedback
			        if (removed) {
			            session.setAttribute("message", "Stock removed successfully.");
			        } else {
			            session.setAttribute("message", "Failed to remove stock.");
			        }
			        
			        response.sendRedirect("mystocks.jsp");
		      

	}

}
