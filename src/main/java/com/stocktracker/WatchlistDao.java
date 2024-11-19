package com.stocktracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class WatchlistDao {
	public Watchlist getWatchList(String username) {
		Watchlist watchlist = new Watchlist(username);

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			//Load the JDBC Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//Establish the connection
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock_price_track_db", "root", "deepik@1511");
			
			//Prepare the SQL query with placeHolders
			String sql = "SELECT s.symbol, s.name, s.current_price FROM stocks s JOIN user_stocks us ON s.symbol = us.symbol WHERE us.username =?";
			
			//create the PreparedStatement object
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, username);
			//Execute the Query
			rs = pstmt.executeQuery();
			
			//Process the result set
			
			while (rs.next()) {
				Stock st = new Stock();
				st.setName(rs.getString("name"));
	            st.setSymbol(rs.getString("symbol"));
	            st.setCurrentPrice(rs.getDouble("current_price"));
	            watchlist.addStockinList(st);
	        }
			
		}catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		
		}finally {
			try { if(rs!= null) rs.close(); } catch (SQLException e) {e.printStackTrace(); }
			try { if(pstmt!= null) pstmt.close(); } catch (SQLException e) {e.printStackTrace(); }
			try { if(conn!= null) conn.close(); } catch (SQLException e) {e.printStackTrace(); }
		}
		return watchlist;
	}
	
	boolean addToWatchList(String username,String symbol) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int row=0;
		
		try {
			// Load JDBC Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// Establishing the connection
			conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/stock_price_track_db","root","deepik@1511");
			
				// Prepared the SQL query with place holders
				String sql = "INSERT INTO user_stocks (username,symbol) VALUES (?,?)";
			
				// Creating the PreparedStatement object
				pstmt = conn.prepareStatement(sql);
			
				// Set the Values for PlaceHolders
				pstmt.setString(1, username);
				pstmt.setString(2, symbol);
				
			
				// Execute the query
				row = pstmt.executeUpdate();
			
				// Process The Result
	
			}catch(SQLException | ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {if(pstmt!=null) pstmt.close();}catch (SQLException e) {e.printStackTrace();}
				try {if(conn!=null) conn.close();}catch (SQLException e) {e.printStackTrace();}
			}
		return row>0;
	}
	
	boolean removeFromWatchList(String username,String symbol) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int row=0;
		
		try {
			// Load JDBC Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// Establishing the connection
			conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/stock_price_track_db","root","deepik@1511");
			
				// Prepared the SQL query with place holders
			   String sql = "DELETE FROM user_stocks WHERE username = ? AND symbol = ?";
			
				// Creating the PreparedStatement object
				pstmt = conn.prepareStatement(sql);
			
				// Set the Values for PlaceHolders
				pstmt.setString(1, username);
				pstmt.setString(2, symbol);
			
	
				// Execute the query
				row = pstmt.executeUpdate();
			
			
				// Process The Result
	
			}catch(SQLException | ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {if(pstmt!=null) pstmt.close();}catch (SQLException e) {e.printStackTrace();}
				try {if(conn!=null) conn.close();}catch (SQLException e) {e.printStackTrace();}
			}
		return row>0;
	}

	public boolean isStockInWatchlist(String username, String symbol) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock_price_track_db", "root", "deepik@1511");
            String sql = "SELECT * FROM user_stocks WHERE username = ? AND symbol = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, symbol);
            rs = pstmt.executeQuery();

            return rs.next();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
