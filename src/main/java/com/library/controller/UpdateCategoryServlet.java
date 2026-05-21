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

@WebServlet("/UpdateCategory") // JavaScript က "UpdateCategory" လို့ ခေါ်ထားလို့ ဒါပဲ သုံးပါ
public class UpdateCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// JavaScript ရဲ့ window.location.href က GET နဲ့ လာမှာမို့ doGet ကို သုံးရပါမယ်
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// JS က 'id' နဲ့ ပို့ထားတာမို့ 'id' လို့ ပြန်ဖတ်ပါ
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");

			// Category Object တည်ဆောက်ခြင်း
			Category cat = new Category();
			cat.setCategoryId(id);
			cat.setCategoryName(name);
			// Description က prompt မှာ မပါတဲ့အတွက် ခေတ္တချန်ထားနိုင်သည် (သို့မဟုတ် Database
			// ထဲက အဟောင်းအတိုင်းထားရန် Repo မှာ ပြင်ပါ)

			// Repository အားသုံး၍ Update လုပ်ခြင်း
			CategoryRepo repo = new CategoryRepo(DBConnect.getConnection());
			boolean f = repo.updateCategory(cat);

			if (f) {
				response.sendRedirect("dashboard.jsp");
			} else {
				request.getSession().setAttribute("errorMsg", "Failed to update!");
				response.sendRedirect("dashboard.jsp");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("dashboard.jsp");
		}
	}
}