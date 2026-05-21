package com.library.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.library.config.DBConnect;
import com.library.model.Title; // Title Model ကို သုံးမည်
import com.library.repository.TitleRepo; // TitleRepo ကို သုံးမည်

@WebServlet("/UpdateTitle") // URL ကို Dashboard က ခေါ်တဲ့အတိုင်း /UpdateTitle လို့ ပြောင်းပါ
public class UpdateTitleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Dashboard JavaScript က GET နဲ့ လာခဲ့ရင်လည်း အလုပ်လုပ်အောင် doPost ကို
		// လွှဲပေးမည်
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// ၁။ Dashboard က ပို့လိုက်တဲ့ ID နဲ့ နာမည်အသစ်ကို လက်ခံခြင်း
			// dashboard.jsp ထဲက editTitle(id, name) မှာ သုံးထားတဲ့ parameter name
			// တွေဖြစ်ရမယ်
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");

			// ၂။ Title Object ထဲသို့ Data ထည့်ခြင်း
			Title title = new Title();
			title.setTitle_id(id);
			title.setTitle_name(name);

			// ၃။ TitleRepo ကို သုံးပြီး Database မှာ Update လုပ်ခြင်း
			TitleRepo repo = new TitleRepo(DBConnect.getConnection());
			boolean f = repo.updateTitle(title);

			HttpSession session = request.getSession();

			if (f) {
				session.setAttribute("succMsg", "Title updated successfully!");
				response.sendRedirect("dashboard.jsp");
			} else {
				session.setAttribute("errorMsg", "Failed to update title!");
				response.sendRedirect("dashboard.jsp");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("dashboard.jsp?error=server_error");
		}
	}
}