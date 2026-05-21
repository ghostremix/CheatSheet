package com.library.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.library.config.DBConnect;
import com.library.model.Category;
import com.library.repository.CategoryRepo;

@WebServlet("/AddCategory")
public class AddCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// UI ကနေ name နဲ့ titleId (ဘယ် title အောက်မှာ ဆောက်မှာလဲ) ဆိုတာ ပို့ပေးရပါမယ်
			String name = request.getParameter("name");
			String tIdStr = request.getParameter("titleId");
			int titleId = (tIdStr != null) ? Integer.parseInt(tIdStr) : 0;

			if (titleId != 0 && name != null) {
				CategoryRepo repo = new CategoryRepo(DBConnect.getConnection());
				Category category = new Category();

				category.setCategoryName(name);
				category.setTitleId(titleId); // Title နှင့် တိုက်ရိုက်ချိတ်ဆက်ခြင်း

				boolean f = repo.addCategory(category);

				if (f) {
					response.sendRedirect("dashboard.jsp");
				} else {
					System.out.println("Data insertion failed!");
					response.sendRedirect("dashboard.jsp");
				}
			} else {
				response.sendRedirect("dashboard.jsp");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("dashboard.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}