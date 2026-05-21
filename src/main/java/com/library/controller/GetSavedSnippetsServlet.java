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
import com.library.model.User;
import com.library.repository.FavoriteRepo;

/**
 * Servlet implementation class GetSavedSnippetsServlet
 */
@WebServlet("/GetSavedSnippetsServlet")
public class GetSavedSnippetsServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		User user = (User) request.getSession().getAttribute("userObj");

		if (user != null) {
			FavoriteRepo repo = new FavoriteRepo(DBConnect.getConnection());
			List<CheatSheet> list = repo.getSavedSnippets(user.getId());

			// JSON string ကို manual တည်ဆောက်ခြင်း
			StringBuilder json = new StringBuilder();
			json.append("["); // Array အစ

			for (int i = 0; i < list.size(); i++) {
				CheatSheet s = list.get(i);
				json.append("{");
				json.append("\"sheetId\":").append(s.getSheetId()).append(",");
				json.append("\"title\":\"").append(escapeJS(s.getTitle())).append("\",");
				json.append("\"content\":\"").append(escapeJS(s.getContent())).append("\"");
				json.append("}");

				if (i < list.size() - 1) {
					json.append(","); // နောက်ဆုံးတစ်ခုမဟုတ်ရင် ကော်မာခံမယ်
				}
			}

			json.append("]"); // Array အဆုံး
			out.print(json.toString());
			out.flush();
		} else {
			out.print("[]");
		}
	}

	// JSON ထဲမှာ Error မတက်အောင် special characters တွေကို escape လုပ်ပေးတဲ့ helper
	private String escapeJS(String str) {
		if (str == null)
			return "";
		return str.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r").replace("\t",
				"\\t");
	}
}