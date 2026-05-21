<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
// Login ဝင်မဝင် စစ်ဆေးခြင်း
if (session.getAttribute("userObj") == null) {
	response.sendRedirect("login.jsp");
	return;
}
// Dashboard ကနေ ပို့လိုက်တဲ့ Category ID ကို ယူခြင်း
String catId = request.getParameter("catId");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>DevHub | Add New Snippet</title>
<link
	href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
<style>
:root {
	--bg-color: #0f172a;
	--card-bg: #1e293b;
	--accent-color: #3b82f6;
	--text-main: #f8fafc;
	--text-dim: #94a3b8;
}

body {
	font-family: 'Inter', sans-serif;
	background-color: var(--bg-color);
	color: var(--text-main);
	margin: 0;
	display: flex;
	justify-content: center;
	align-items: center;
	min-height: 100vh;
}

.form-container {
	background: var(--card-bg);
	padding: 2rem;
	border-radius: 12px;
	width: 100%;
	max-width: 600px;
	box-shadow: 0 10px 25px rgba(0, 0, 0, 0.3);
	border: 1px solid rgba(255, 255, 255, 0.1);
}

.header {
	display: flex;
	align-items: center;
	gap: 10px;
	margin-bottom: 1.5rem;
}

.header i {
	color: var(--accent-color);
	font-size: 1.5rem;
}

.header h2 {
	margin: 0;
	font-size: 1.5rem;
}

.form-group {
	margin-bottom: 1.2rem;
}

label {
	display: block;
	margin-bottom: 0.5rem;
	color: var(--text-dim);
	font-size: 0.9rem;
}

input[type="text"], textarea {
	width: 100%;
	padding: 12px;
	background: #0f172a;
	border: 1px solid #334155;
	border-radius: 6px;
	color: white;
	font-family: 'Inter', sans-serif;
	box-sizing: border-box;
}

textarea {
	height: 250px;
	font-family: 'Consolas', 'Monaco', monospace;
	resize: vertical;
}

input:focus, textarea:focus {
	outline: none;
	border-color: var(--accent-color);
}

.btn-group {
	display: flex;
	gap: 10px;
	margin-top: 1rem;
}

.btn {
	flex: 1;
	padding: 12px;
	border: none;
	border-radius: 6px;
	cursor: pointer;
	font-weight: 600;
	transition: 0.3s;
}

.btn-save {
	background: var(--accent-color);
	color: white;
}

.btn-save:hover {
	background: #2563eb;
}

.btn-cancel {
	background: #334155;
	color: white;
	text-decoration: none;
	text-align: center;
}

.btn-cancel:hover {
	background: #475569;
}
</style>
</head>
<body>

	<div class="form-container">
		<div class="header">
			<i class="fas fa-code"></i>
			<h2>Add New Snippet</h2>
		</div>

		<form action="AddSnippetServlet" method="post">
			<%-- Hidden field နဲ့ catId ကို Servlet ဆီ ပို့ပေးမယ် --%>
			<input type="hidden" name="categoryId" value="<%=catId%>">

			<div class="form-group">
				<label for="title">Snippet Title</label> <input type="text"
					id="title" name="title" placeholder="e.g. Java Database Connection"
					required>
			</div>

			<div class="form-group">
				<label for="content">Code Content</label>
				<textarea id="content" name="content"
					placeholder="Paste your code here..." required></textarea>
			</div>

			<div class="btn-group">
				<a href="dashboard.jsp" class="btn btn-cancel">Cancel</a>
				<button type="submit" class="btn btn-save">Save Snippet</button>
			</div>
		</form>
	</div>

</body>
</html>