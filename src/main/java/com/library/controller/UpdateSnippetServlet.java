package com.library.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.library.config.DBConnect;
import com.library.model.Category;
import com.library.model.CheatSheet;
import com.library.repository.CheatSheetRepo;

/**
 * Servlet implementation class UpdateSnippetServlet
 */
@WebServlet("/UpdateSnippetServlet")
public class UpdateSnippetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateSnippetServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Form data များကို လက်ခံခြင်း
			int id = Integer.parseInt(request.getParameter("sheetId"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int categoryId = Integer.parseInt(request.getParameter("categoryId"));

			// Model object တည်ဆောက်ခြင်း
			CheatSheet sheet = new CheatSheet();
			sheet.setSheetId(id);
			sheet.setTitle(title);
			sheet.setContent(content);

			Category cat = new Category();
			cat.setCategoryId(categoryId);
			sheet.setCategory(cat);

			// Repo ကိုသုံးပြီး DB တွင် update လုပ်ခြင်း
			CheatSheetRepo repo = new CheatSheetRepo(DBConnect.getConnection());
			boolean f = repo.updateCheatsheet(sheet);

			if (f) {
				response.sendRedirect("dashboard.jsp");
			} else {
				response.getWriter().println("Failed to update snippet.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
