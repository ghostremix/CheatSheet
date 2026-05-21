package com.library.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.library.config.DBConnect;
import com.library.repository.TitleRepo; // TitleRepo ကို ပြောင်းသုံးပါ

@WebServlet("/DeleteTitle") // URL Mapping ကို /DeleteTitle လို့ တိုတိုလေး ပြောင်းထားပါတယ်
public class DeleteTitleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// ၁။ ဖျက်မယ့် Title ID ကို ယူမယ်
			int titleId = Integer.parseInt(request.getParameter("id"));

			// ၂။ TitleRepo ကို သုံးပြီး ဖျက်မယ်
			TitleRepo repo = new TitleRepo(DBConnect.getConnection());

			// ဒီ Method က Database ထဲက title table က row ကို ဖျက်မှာပါ
			// ON DELETE CASCADE ရှိရင် ၎င်းနှင့်ဆိုင်သော category များ အလိုလို ပျက်ပါမည်
			boolean f = repo.deleteTitle(titleId);

			if (f) {
				response.sendRedirect("dashboard.jsp");
			} else {
				response.sendRedirect("dashboard.jsp?error=delete_failed");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("dashboard.jsp?error=invalid_id");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}