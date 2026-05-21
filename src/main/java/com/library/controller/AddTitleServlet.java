package com.library.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.library.config.DBConnect;
import com.library.repository.TitleRepo; // TitleRepo ကို သုံးမယ်

@WebServlet("/AddTitle") // URL ကို ခေါ်ရလွယ်အောင် /AddTitle လို့ပဲ ထားလိုက်ပါ
public class AddTitleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// GET နဲ့ လာရင်လည်း POST ကိုပဲ လွှတ်လိုက်မယ် (Edit mode က ခလုတ်တွေအတွက်)
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ၁။ Form ကနေ titleName ကို လက်ခံမယ်
		String titleName = request.getParameter("titleName");

		if (titleName != null && !titleName.trim().isEmpty()) {
			// ၂။ TitleRepo ကို သုံးပြီး Database ထဲ ထည့်မယ်
			TitleRepo repo = new TitleRepo(DBConnect.getConnection());
			boolean f = repo.addTitle(titleName);

			if (f) {
				// အောင်မြင်ရင် Dashboard ပြန်သွားမယ်
				response.sendRedirect("dashboard.jsp");
			} else {
				// မအောင်မြင်ရင် Error message နဲ့ ပြန်သွားမယ်
				response.sendRedirect("dashboard.jsp?error=failed");
			}
		} else {
			// နာမည်မပါလာရင် Dashboard ပဲ ပြန်ပို့မယ်
			response.sendRedirect("dashboard.jsp");
		}
	}
}