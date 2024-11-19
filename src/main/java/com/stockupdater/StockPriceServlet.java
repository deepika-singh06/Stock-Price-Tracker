package com.stockupdater;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class StockPriceServlet
 */

@WebServlet("/UpdateStockData") //optional: for manual triggers
public class StockPriceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String API_KEY = "PX8FSXMWU1AMFZQU";
	private static final String[] SYMBOLS = {"HDFCBANK.BSE", "RELIANCE.BSE","TCS.BSE", "INFY.BSE", "ICICIBANK.BSE",
			"SBIN.BSE", "TATAMOTORS.BSE", "BAJFINANCE.BSE", "AXISBANK.BSE", "LT.BSE"};
	private static final String DB_URL = "jdbc:mysql://localhost:3306/stock_price_track_db?usesSSL=false";
	private static final String USER = "root";
	private static final String PASS = "deepik@1511";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			updateAllStocks();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	//Method to fetch and update all stocks data
		
	public void updateAllStocks() throws ClassNotFoundException{
		for(String symbol: SYMBOLS) {
			try {
				//Establishing Connection to API URL
				String apiUrl ="https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + symbol + "&apikey=" +API_KEY;
				
						@SuppressWarnings("deprecation")
						HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
						conn.setRequestMethod("GET");
						
						BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						String inputLine;
						StringBuilder content = new StringBuilder();
						while((inputLine = in.readLine()) != null) {
							content.append(inputLine);
						}
						in.close();
						conn.disconnect();
						
						JSONObject jsonResponse = new JSONObject(content.toString());
						System.out.println("API Response for " + symbol + ": " + jsonResponse.toString());
						
						if(jsonResponse.has("Global Quote")) {
							JSONObject globalQuote = jsonResponse.getJSONObject("Global Quote");
							
							String stockName = symbol.split("\\.")[0]; //Assuming the name is part of the symbol
							String openPrice = globalQuote.getString("02. open");
							String highPrice = globalQuote.getString("03. high");
							String lowPrice = globalQuote.getString("04. low");
							String currentPrice = globalQuote.getString("05. price");
							String volume = globalQuote.getString("06. volume");
							String latestTradingDay = globalQuote.getString("07. latest trading day");
							String previousClose = globalQuote.getString("08. previous close");
							String priceChange = globalQuote.getString("09. change");
							String changePercent = globalQuote.getString("10. change percent");
							
							if(stockExist(symbol)) {	
							updateStockData(stockName,symbol,openPrice,highPrice,lowPrice,currentPrice,volume,latestTradingDay,
									previousClose,priceChange,changePercent);
							}else {
								insertStockData(stockName,symbol,openPrice,highPrice,lowPrice,currentPrice,volume,latestTradingDay,
										previousClose,priceChange,changePercent);
							}
						}else {
							System.out.println("Global Quote not found for symbol: "+ symbol);
						}
						
					} catch (IOException | JSONException e) {
						e.printStackTrace();
					}
				}
			}
			private void updateStockData(String stockName, String symbol, String openPrice, String highPrice, String lowPrice,
							String currentPrice, String volume, String latestTradingDay, String previousClose, String priceChange, 
							String changePercent) throws ClassNotFoundException {
						           String query = """
		           		                 UPDATE stocks SET
		           		                 name = ?,
		           		                 open_price = ?,
		           		                 high_price = ?,
		           		                 low_price = ?,
		           		                 current_price = ?,
		           		                 volume = ?,
		           		                 latest_trading_day = ?,
		           		                 previous_close = ?,
		           		                 price_change = ?,
		           		                 change_percent = ?
		           		                 WHERE symbol = ?
		           		                 """;
		          
		           //Load the JDBC Driver
					Class.forName("com.mysql.cj.jdbc.Driver");
					try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
						PreparedStatement pstmt = conn.prepareStatement(query)) {
						 
						pstmt.setString(1, stockName);
						pstmt.setBigDecimal(2, new BigDecimal(openPrice));
						pstmt.setBigDecimal(3, new BigDecimal(highPrice));
						pstmt.setBigDecimal(4, new BigDecimal(lowPrice));
						pstmt.setBigDecimal(5, new BigDecimal(currentPrice));
						pstmt.setLong(6,  Long.parseLong(volume));
						pstmt.setDate(7,  Date.valueOf(latestTradingDay));
						pstmt.setBigDecimal(8, new BigDecimal(previousClose));
						pstmt.setBigDecimal(9, new BigDecimal(priceChange));
						pstmt.setString(10, changePercent);
						pstmt.setString(11, symbol);
						
						pstmt.executeUpdate();
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
			
			private boolean stockExist(String symbol) throws ClassNotFoundException{
				ResultSet rs = null;
				String query = "SELECT * FROM stocks WHERE symbol = ?";
				
				Class.forName("com.mysql.cj.jdbc.Driver");
				try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
					PreparedStatement pstmt = conn.prepareStatement(query)) {
					pstmt.setString(1, symbol);
					
					rs = pstmt.executeQuery();
					
					return rs.next();
					
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
				
			}
			
			private void insertStockData(String stockName, String symbol, String openPrice, String highPrice,
					String lowPrice, String currentPrice, String volume, String latestTradingDay, String previousClose, String priceChange,
					String changePercent) throws ClassNotFoundException {
				String query = """
		                INSERT INTO stocks (
		                name,
						open_price,
						high_price,
						low_price,
						current_price,
						volume,
						latest_trading_day,
						previous_close,
						price_change,
						change_percent,
						symbol)
		                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
		                """;
				
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				try(Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
					PreparedStatement pstmt = conn.prepareStatement(query)) {
					
					pstmt.setString(1, stockName);
					pstmt.setBigDecimal(2, new java.math.BigDecimal(openPrice));
					pstmt.setBigDecimal(3, new java.math.BigDecimal(highPrice));
					pstmt.setBigDecimal(4, new java.math.BigDecimal(lowPrice));
					pstmt.setBigDecimal(5, new java.math.BigDecimal(currentPrice));
					pstmt.setLong(6, Long.parseLong(volume));
					pstmt.setDate(7, Date.valueOf(latestTradingDay));
					pstmt.setBigDecimal(8, new java.math.BigDecimal(previousClose));
					pstmt.setBigDecimal(9, new java.math.BigDecimal(priceChange));
					pstmt.setString(10, changePercent);
					pstmt.setString(11, symbol);
			        
			        pstmt.executeUpdate();
					
				} catch (SQLException e) {
					e.printStackTrace();
					
				}
			}
		}
				
				
 
