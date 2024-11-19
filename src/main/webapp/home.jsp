<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="com.stocktracker.User" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home Page</title>
<link rel="stylesheet" href="allnavbar.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"  />

</head>
<%
	session = request.getSession(false);
	if(session == null||session.getAttribute("user") == null){
		response.sendRedirect("login.jsp");
		return;
	}
	User user = new User(); 
	user = (User) session.getAttribute("user");
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