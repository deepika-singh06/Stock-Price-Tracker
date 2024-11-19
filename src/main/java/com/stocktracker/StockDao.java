package com.stocktracker;

import java.sql.*;



public class StockDao {
	public Stock getAllStock(){
		Stock stocks = new Stock();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			//Load the JDBC Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//Establish the connection
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock_price_track_db", "root", "deepik@1511");
			
			//Prepare the SQL query with placeHolders
			String sql = "SELECT * FROM stocks";
			
			//create the PreparedStatement object
			pstmt = conn.prepareStatement(sql);
			
			
			//Execute the Query
			rs = pstmt.executeQuery();
			
            while(rs.next()) {
            	// Create a new Stock instance for each row
                Stock stock = new Stock();
            	stock.setName(rs.getString("name"));
				stock.setSymbol(rs.getString("symbol"));
				stock.setCurrentPrice(rs.getDouble("current_price"));
				
				 // Add the new stock to the list
                stocks.addStockInList(stock);
				
			}
		}catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		
		}finally {
			try { if(rs!= null) rs.close(); } catch (SQLException e) {e.printStackTrace(); }
			try { if(pstmt!= null) pstmt.close(); } catch (SQLException e) {e.printStackTrace(); }
			try { if(conn!= null) conn.close(); } catch (SQLException e) {e.printStackTrace(); }
		}
		return stocks; // Return the list


		
	}
	

	public Stock getStockBySymbol(String symbol) {
		Stock stock = new Stock();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        // Load the JDBC Driver
	        Class.forName("com.mysql.cj.jdbc.Driver");

	        // Establish the connection
	        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock_price_track_db", "root", "deepik@1511");

	        // Prepare the SQL query with placeHolders
	        String sql = "SELECT * FROM stocks WHERE symbol = ?";

	        // Create the PreparedStatement object
	        pstmt = conn.prepareStatement(sql);

	        // Set the stock symbol
	        pstmt.setString(1, symbol);

	        // Execute the Query
	        rs = pstmt.executeQuery();

	        if (rs.next()) {

				stock.setName(rs.getString("name"));
				stock.setSymbol(rs.getString("symbol"));
				stock.setOpenPrice(rs.getDouble("open_price"));
				stock.setHighPrice(rs.getDouble("high_price"));
				stock.setLowPrice(rs.getDouble("low_price"));
				stock.setCurrentPrice(rs.getDouble("current_price"));
				stock.setLatestTradingDay(rs.getString("latest_trading_day"));
				stock.setVolume(rs.getLong("volume"));
				stock.setPreviousClose(rs.getDouble("previous_close"));
				stock.setPriceChange(rs.getDouble("price_change"));
				stock.setChangePercent(rs.getInt("change_percent"));
	        }
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
	    return stock;
	}
	
}
