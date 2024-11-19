package com.stocktracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDao {
	User validateUser(String username, String password) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = new User();
		try {
			//Load the JDBC Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//Establish the connection
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock_price_track_db", "root", "deepik@1511");
			
			//Prepare the SQL query with placeHolders
			String sql = "SELECT* FROM users WHERE username =? AND password =?";
			
			//create the PreparedStatement object
			pstmt = conn.prepareStatement(sql);
			
			//set the values for the placeHolder
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			
			//Execute the Query
			rs = pstmt.executeQuery();
			
			//Process the result set
			
			if(rs.next()) {
				
				user.setUsername(rs.getString("username"));	
				user.setPassword(rs.getString("password"));
			}else {
				user = null;
			}
			
		}catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		
		}finally {
			try { if(rs!= null) rs.close(); } catch (SQLException e) {e.printStackTrace(); }
			try { if(pstmt!= null) pstmt.close(); } catch (SQLException e) {e.printStackTrace(); }
			try { if(conn!= null) conn.close(); } catch (SQLException e) {e.printStackTrace(); }
		}
		return user;
	}
	
	boolean registerUser(String username,String password) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int row=0;
		
		try {
			// Load JDBC Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// Establishing the connection
			conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/stock_price_track_db","root","deepik@1511");
			
				// Prepared the SQL query with place holders
				String sql = "INSERT INTO users (username,password) VALUES (?,?)";
			
				// Creating the PreparedStatement object
				pstmt = conn.prepareStatement(sql);
			
				// Set the Values for PlaceHolders
				pstmt.setString(1, username);
				pstmt.setString(2, password);
			
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
	
	boolean updatePassword(String username,String oldpassword,String newpassword) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rows = 0;
		try {
			// Load JDBC Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// Establishing the connection
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock_price_track_db","root","deepik@1511");
			
			// Prepared the SQL query with place holders
			String sql = "UPDATE users SET password = ? WHERE username = ? AND password = ?";
			
			// Creating the PreparedStatement object
			pstmt = conn.prepareStatement(sql);
			
			// Set the Values for PlaceHolders
			pstmt.setString(1, newpassword);
			pstmt.setString(2, username);
			pstmt.setString(3, oldpassword);
			
			// Execute the query
			rows = pstmt.executeUpdate();
			
			// Process The Result
			
		}catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {if(pstmt!=null) pstmt.close();}catch (SQLException e) {e.printStackTrace();}
			try {if(conn!=null) conn.close();}catch (SQLException e) {e.printStackTrace();}
		}
		return rows>0;
	}

	boolean deleteUser(String password,String username) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rows=0;
		try {
			// Load JDBC Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// Establishing the connection
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock_price_track_db","root","deepik@1511");
			
			// Prepared the SQL query with place holders
			String sql = "DELETE FROM users WHERE username = ? AND password = ?";
			
			// Creating the PreparedStatement object
			pstmt = conn.prepareStatement(sql);
			
			// Set the Values for PlaceHolders
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			
			// Execute the query
			rows = pstmt.executeUpdate();
			
			// Process The Result
			
		}catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {if(pstmt!=null) pstmt.close();}catch (SQLException e) {e.printStackTrace();}
			try {if(conn!=null) conn.close();}catch (SQLException e) {e.printStackTrace();}
		}
		return rows>0;
	}

}
