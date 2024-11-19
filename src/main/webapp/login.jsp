<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login Page</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
<div class="container">
   <div class="signup-box">
     <h2>Login</h2>
	   <form action="loginservlet" method="post">
			<div class="input-box">
			<input type="text" name="username" id="username" placeholder="Username" required>
			</div>
			
			<div class="input-box">
			<input type="password" name="password" id="password" placeholder="Password" required>
			</div>
			<button class="submit-btn" type="submit">Login</button>
	   </form>
	  <h5>New User? <a href="index.html" >Register here</a></h5>
	</div>
</div>
   
</body>
</html>