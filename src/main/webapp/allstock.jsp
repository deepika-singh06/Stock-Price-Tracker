<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ page import="com.stocktracker.*" %>
     <%@ page import="com.stockupdater.*" %>
      <%@ page import="java.util.*" %>
      
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Available Stock</title>
<link rel="stylesheet" href="allnavbar.css">
<link rel="stylesheet" href="allstocktable.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"  />
</head>
<%
	session = request.getSession(false);
	if(session == null||session.getAttribute("user") == null){
		response.sendRedirect("login.jsp");
		return;
	}
	 User user = (User ) session.getAttribute("user");
	 Stock stockList = (Stock) session.getAttribute("stocklist");
%>
<body>
    <header>  
	<div class="navbar">
	 <div class="logo">
	 <h1>Stock Price Tracker</h1>
	 <p>Welcome, <%= user.getUsername() %>!</p>
	 </div>
			<ul class="links">
				<li><a href="stocklistservlet">All Stocks</a></li>
				<li><a href="watchlistservlet">My Stocks</a></li>
				<li><a href="update.jsp">Change Password</a></li>
				<li><a href="delete.jsp">Delete account</a></li>
				<li><a href="logout">Log Out</a></li>	
			</ul>
			<div class="toggle_btn">
			<i class="fa-solid fa-bars"></i>
			</div>	
	</div>
	
	<div class="dropdown_menu ">
	       <ul>
	           <li><a href="stocklistservlet">All Stocks</a></li>
				<li><a href="watchlistservlet">My Stocks</a></li>
				<li><a href="update.jsp">Change Password</a></li>
				<li><a href="delete.jsp">Delete account</a></li>
				<li><a href="logout">Log Out</a></li>	
     	   </ul>
	</div>
	</header>
	<div class="container">
	<h2>Available Stocks</h2>

   <table class="tbl">
    <thead>
        <tr>
            <th>Symbol</th>
            <th>Stock Name</th>
            <th>Current Price</th>
            <th>Action</th>
        </tr>
    </thead> 
    <tbody>
        <% 
            if(stockList != null) {
                for(Stock stock : stockList.getAllStockList()) {
        %>
            <tr>
                <td data-label="Symbol"><%= stock.getSymbol() %></td>
                <td data-label="Stock Name"><%= stock.getName() %></td>
                <td data-label="Current Price"><%= stock.getCurrentPrice() %></td>
                <td data-label="Action">
                    <a href="stockdetailservlet?symbol=<%= stock.getSymbol() %>">
                        <button id="view">View Details</button>
                    </a>
                    <form action="addstockservlet" method="post">
                        <input type="hidden" name="symbol" value="<%= stock.getSymbol() %>">
                        <input type="submit" id="add" value="Add to Watchlist">
                    </form>
                </td>
            </tr>
        <% 
                }
            } else { 
        %>
            <tr>
                <td colspan="4">No stocks available</td>
            </tr>
        <% 
            } 
        %>
    </tbody>
</table>
</div>
	
    <script>
	   const toggleBtn = document.querySelector('.toggle_btn')
	   const toggleBtnIcon = document.querySelector('.toggle_btn i')
	   const dropDownMenu = document.querySelector('.dropdown_menu')
	   
	      toggleBtn.onclick = function () {
		   dropDownMenu.classList.toggle('open')
		   const isopen = dropDownMenu.classList.contains('open')
		   
		   toggleBtnIcon.classList = isopen
		   ?'fa-solid fa-xmark'
		    :'fa-solid fa-bars'  
	   }
	</script>
	
</body>
</html>