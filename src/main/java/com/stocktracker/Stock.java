package com.stocktracker;
import java.util.ArrayList;
import java.util.List;


public class Stock {
	private String name;
	private String symbol;
	private double openPrice;
	private double highPrice;
	private double lowPrice;
	private double currentPrice;
	private String latestTradingDay;
	private long volume;
	private double previousClose;
	private double priceChange;
	private int changePercent;
    private List<Stock> allStocksList;
	
	public Stock() {
		allStocksList = new ArrayList<Stock>();
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public double getOpenPrice() {
		return openPrice;
	}
	public void setOpenPrice(double openPrice) {
		this.openPrice = openPrice;
	}
	public double getHighPrice() {
		return highPrice;
	}
	public void setHighPrice(double highPrice) {
		this.highPrice = highPrice;
	}
	public double getLowPrice() {
		return lowPrice;
	}
	public void setLowPrice(double lowPrice) {
		this.lowPrice = lowPrice;
	}
	public double getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}
	public String getLatestTradingDay() {
		return latestTradingDay;
	}
	public void setLatestTradingDay(String latestTradingDay) {
		this.latestTradingDay = latestTradingDay;
	}
	public long getVolume() {
		return volume;
	}
	public void setVolume(long volume) {
		this.volume = volume;
	}
	public double getPreviousClose() {
		return previousClose;
	}
	public void setPreviousClose(double previousClose) {
		this.previousClose = previousClose;
	}
	public double getPriceChange() {
		return priceChange;
	}
	public void setPriceChange(double priceChange) {
		this.priceChange = priceChange;
	}
	public int getChangePercent() {
		return changePercent;
	}
	public void setChangePercent(int changePercent) {
		this.changePercent = changePercent;
	}
	
	public List<Stock> getAllStockList(){
		return allStocksList;
	}
	
	public void addStockInList(Stock st) {
		allStocksList.add(st);
	}
	

}
