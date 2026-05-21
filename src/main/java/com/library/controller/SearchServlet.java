package com.library.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.library.config.DBConnect;
import com.library.model.CheatSheet;
import com.library.repository.CheatSheetRepo;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		String query = request.getParameter("query");
		if (query == null || query.trim().isEmpty()) {
			out.print("[]");
			return;
		}

		CheatSheetRepo repo = new CheatSheetRepo(DBConnect.getConnection());
		List<CheatSheet> results = repo.searchEverything(query.trim());

		// Manual JSON တည်ဆောက်ခြင်း
		StringBuilder json = new StringBuilder();
		json.append("[");
		for (int i = 0; i < results.size(); i++) {
			CheatSheet s = results.get(i);
			json.append("{");
			json.append("\"id\":").append(s.getSheetId()).append(",");
			json.append("\"title\":\"").append(escapeJS(s.getTitle())).append("\",");
			json.append("\"catId\":\"").append(s.getCategory().getCategoryId()).append("\",");
			json.append("\"catName\":\"").append(escapeJS(s.getCategory().getCategoryName())).append("\"");
			json.append("}");
			if (i < results.size() - 1)
				json.append(",");
		}
		json.append("]");

		out.print(json.toString());
		out.flush();
	}

	private String escapeJS(String str) {
		if (str == null)
			return "";
		return str.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
	}
}