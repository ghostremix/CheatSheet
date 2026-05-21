<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>DevHub | Login</title>
<link rel="stylesheet" href="form.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@6.4.2/css/all.min.css">
</head>
<body>

	<div class="container">
		<a href="login.jsp" class="brand-logo"> <i class="fas fa-bolt"></i>
			<span>DevHub</span>
		</a>

		<div class="heading">Login to your account</div>

		<%
		String error = (String) session.getAttribute("errorMsg");
		if (error != null) {
		%>
		<div class="error-text"><%=error%></div>
		<%
		session.removeAttribute("errorMsg");
		}
		%>

		<form class="form" action="LoginServlet" method="post">
			<div class="input-field">
				<input required autocomplete="off" type="email" name="email"
					id="email" /> <label for="email">Email</label>
			</div>

			<div class="input-field">
				<input required autocomplete="off" type="password" name="password"
					id="password" /> <label for="password">Password</label>
			</div>

			<div class="btn-container">
				<button type="submit" class="btn">Login</button>
				<div class="acc-text">
					New here? <a href="register.jsp"
						style="color: #0034de; text-decoration: none; font-weight: bold;">Create
						Account</a>
				</div>
			</div>
		</form>
	</div>
</body>
</html>