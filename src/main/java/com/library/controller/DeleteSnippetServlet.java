package com.library.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.library.config.DBConnect;
import com.library.repository.CheatSheetRepo;

@WebServlet("/DeleteSnippet")
public class DeleteSnippetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// URL ကနေပါလာတဲ့ id ကို ယူခြင်း (ဥပမာ- DeleteSnippetServlet?id=5)
			int id = Integer.parseInt(request.getParameter("id"));

			// Repo ကိုသုံးပြီး Database ထဲက ဖျက်ခြင်း
			CheatSheetRepo repo = new CheatSheetRepo(DBConnect.getConnection());
			boolean f = repo.deleteCheatsheet(id);

			HttpSession session = request.getSession();

			if (f) {
				// အောင်မြင်ရင် Dashboard ကို ပြန်သွားမယ်
				response.sendRedirect("dashboard.jsp");
			} else {
				// မအောင်မြင်ရင် Message တစ်ခုခု ပြနိုင်သည်
				session.setAttribute("errorMsg", "Failed to delete snippet!");
				response.sendRedirect("dashboard.jsp");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("dashboard.jsp");
		}
	}
}