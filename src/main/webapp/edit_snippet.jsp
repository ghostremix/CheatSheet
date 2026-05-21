<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="com.library.repository.*, com.library.model.*, com.library.config.DBConnect"%>
<%
// Login စစ်ဆေးခြင်း
if (session.getAttribute("userObj") == null) {
	response.sendRedirect("login.jsp");
	return;
}

// URL ကနေပါလာတဲ့ snippet id ကိုယူပြီး data အဟောင်း ပြန်ဆွဲထုတ်ခြင်း
int sheetId = Integer.parseInt(request.getParameter("id"));
CheatSheetRepo repo = new CheatSheetRepo(DBConnect.getConnection());
CheatSheet sheet = repo.getCheatsheetById(sheetId); // ဒီ method ကို Repo ထဲမှာ ရှိဖို့လိုပါတယ်
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>DevHub | Edit Snippet</title>
<link
	href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
<style>
/* add_snippet.jsp က style တွေကို ပြန်သုံးနိုင်ပါတယ် */
body {
	font-family: 'Inter', sans-serif;
	background-color: #0f172a;
	color: white;
	display: flex;
	justify-content: center;
	align-items: center;
	min-height: 100vh;
	margin: 0;
}

.form-container {
	background: #1e293b;
	padding: 2rem;
	border-radius: 12px;
	width: 100%;
	max-width: 600px;
}

.form-group {
	margin-bottom: 1.2rem;
}

label {
	display: block;
	margin-bottom: 0.5rem;
	color: #94a3b8;
}

input[type="text"], textarea {
	width: 100%;
	padding: 12px;
	background: #0f172a;
	border: 1px solid #334155;
	border-radius: 6px;
	color: white;
	box-sizing: border-box;
}

textarea {
	height: 300px;
	font-family: 'Consolas', monospace;
}

.btn-group {
	display: flex;
	gap: 10px;
	margin-top: 1rem;
}

.btn-update {
	flex: 1;
	background: #3b82f6;
	color: white;
	padding: 12px;
	border: none;
	border-radius: 6px;
	cursor: pointer;
	font-weight: bold;
}

.btn-cancel {
	flex: 1;
	background: #334155;
	color: white;
	padding: 12px;
	border: none;
	border-radius: 6px;
	text-decoration: none;
	text-align: center;
	font-weight: bold;
}
</style>
</head>
<body>

	<div class="form-container">
		<h2 style="margin-top: 0;">
			<i class="fas fa-edit"></i> Edit Snippet
		</h2>

		<form action="UpdateSnippetServlet" method="post">
			<!-- ID များကို Hidden ဖြင့် ပို့ခြင်း -->
			<input type="hidden" name="sheetId" value="<%=sheet.getSheetId()%>">
			<input type="hidden" name="categoryId"
				value="<%=sheet.getCategory().getCategoryId()%>">

			<div class="form-group">
				<label>Snippet Title</label> <input type="text" name="title"
					value="<%=sheet.getTitle()%>" required>
			</div>

			<div class="form-group">
				<label>Code Content</label>
				<textarea name="content" required><%=sheet.getContent()%></textarea>
			</div>

			<div class="btn-group">
				<a href="dashboard.jsp" class="btn btn-cancel">Cancel</a>
				<button type="submit" class="btn btn-update">Update Snippet</button>
			</div>
		</form>
	</div>

</body>
</html>