<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ page import="com.stocktracker.*" %>
      <%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>WatchList</title>
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
	 Watchlist mystocklist = (Watchlist) session.getAttribute("watchlist");
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
	<h2>My Stocks</h2>
	
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
			   if(mystocklist != null){
				   for(Stock mystock : mystocklist.getMywatchList()){
					   if(mystock != null){
				   
			   %>
			    <tr>
			        <td data-label="Stock Name"><%= mystock.getName() %></td>
			        <td data-label="Symbol"><%= mystock.getSymbol() %></td>
			        <td data-label="Current Price"><%= mystock.getCurrentPrice() %></td> 
			        <td data-label="Action" >
			        <a href="removestockservlet?username=<%= user.getUsername() %> &symbol=<%= mystock.getSymbol() %>"><button id="remove">Remove</button></a></td>
			    </tr>		   
			  <% 
			        }  
				  } 
			   } else {
		            %>
		            <tr>
		                <td colspan="4">No stocks in your watchlist</td>
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