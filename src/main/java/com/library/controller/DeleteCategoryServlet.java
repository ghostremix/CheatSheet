package com.library.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.library.config.DBConnect;
import com.library.repository.CategoryRepo;

// JavaScript ထဲက window.location.href = "DeleteCategory" နှင့် ကိုက်ညီအောင် ပြင်ဆင်ခြင်း
@WebServlet("/DeleteCategory")
public class DeleteCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// URL မှ id ကိုလက်ခံခြင်း (DeleteCategory?id=...)
			String idStr = request.getParameter("id");

			if (idStr != null) {
				int id = Integer.parseInt(idStr);

				CategoryRepo repo = new CategoryRepo(DBConnect.getConnection());
				boolean f = repo.deleteCategory(id);

				HttpSession session = request.getSession();

				if (f) {
					// အောင်မြင်ရင် Dashboard ကို ပြန်သွားမယ်
					response.sendRedirect("dashboard.jsp");
				} else {
					// မအောင်မြင်ရင် (ဥပမာ- ၎င်းအောက်မှာ snippet တွေ ရှိနေရင်) error ပြမယ်
					session.setAttribute("errorMsg", "Cannot delete! Please delete related snippets first.");
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
}