<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>DevHub | Register</title>
<link rel="stylesheet" href="form.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@6.4.2/css/all.min.css">
</head>
<body>
	<div class="container">
		<a href="login.jsp" class="brand-logo"> <i class="fas fa-bolt"></i>
			<span>DevHub</span>
		</a>

		<div class="heading">Register to your account</div>
		<form class="form" action="RegisterServlet" method="post">
			<div class="input-field">
				<input required autocomplete="off" type="text" name="username"
					id="username" /> <label for="username">Name</label>
			</div>
			<div class="input-field">
				<input required autocomplete="off" type="email" name="email"
					id="email" /> <label for="email">Email</label>
			</div>
			<div class="input-field">
				<input required autocomplete="off" type="password" name="password"
					id="password" /> <label for="password">Password</label>
			</div>

			<div class="btn-container">
				<button class="btn">Submit</button>

				<div class="acc-text">
					Already have an account? <a href="login.jsp">Login</a>
				</div>
			</div>
		</form>
	</div>

</body>
</html>