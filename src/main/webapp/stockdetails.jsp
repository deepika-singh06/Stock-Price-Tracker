<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ page import="com.stocktracker.*" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Stock Details</title>
<link rel="stylesheet" href="allnavbar.css">
<link rel="stylesheet" href="stockdetails.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"  />
</head>
<%
	session = request.getSession(false);
	if(session == null||session.getAttribute("user") == null){
		response.sendRedirect("login.jsp");
		return;
	}
	 User user = (User ) session.getAttribute("user");
	 
	 Stock stockDetail = (Stock) session.getAttribute("stockDetail");
	
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
	
	<table border="1">
    <thead>
        <tr>
            <th colspan="2">Stock Details</th>
        </tr>
    </thead>
    <tbody>
        <%
            if(stockDetail != null) {
        %>
            <tr>
                <td>Symbol</td>
                <td><%= stockDetail.getSymbol() %></td>
            </tr>
            <tr>
                <td>Stock Name</td>
                <td><%= stockDetail.getName() %></td>
            </tr>
            <tr>
                <td>Current Price</td>
                <td><%= stockDetail.getCurrentPrice() %></td>
            </tr>
            <tr>
                <td>Open Price</td>
                <td><%= stockDetail.getOpenPrice() %></td>
            </tr>
            <tr>
                <td>High Price</td>
                <td><%= stockDetail.getHighPrice() %></td>
            </tr>
            <tr>
                <td>Low Price</td>
                <td><%= stockDetail.getLowPrice() %></td>
            </tr>
            <tr>
                <td>Volume</td>
                <td><%= stockDetail.getVolume() %></td>
            </tr>
            <tr>
                <td>Latest Trading Day</td>
                <td><%= stockDetail.getLatestTradingDay() %></td>
            </tr>
            <tr>
                <td>Previous Close</td>
                <td><%= stockDetail.getPreviousClose() %></td>
            </tr>
            <tr>
                <td>Price Change</td>
                <td><%= stockDetail.getPriceChange() %></td>
            </tr>
            <tr>
                <td>Change Percent</td>
                <td><%= stockDetail.getChangePercent() %></td>
            </tr>
            <tr>
                <td>Action</td>
                <td>
                    <form action="addstockservlet" method="post">
                        <input type="hidden" name="symbol" value="<%= stockDetail.getSymbol() %>">
                        <input type="submit" value="Add to Watchlist">
                    </form>
                </td>
            </tr>
        <%
            } else {
        %>
            <tr>
                <td colspan="2">No Stock Details available</td>
            </tr>
        <%
            }
        %>
    </tbody>
</table>
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