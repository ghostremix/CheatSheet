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
 * Servlet implementation class AddSnippetServlet
 */
@WebServlet("/AddSnippetServlet")
public class AddSnippetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddSnippetServlet() {
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
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		int categoryId = Integer.parseInt(request.getParameter("categoryId"));

		Category cat = new Category();
		cat.setCategoryId(categoryId);

		CheatSheet sheet = new CheatSheet();
		sheet.setTitle(title);
		sheet.setContent(content);
		sheet.setCategory(cat);

		CheatSheetRepo repo = new CheatSheetRepo(DBConnect.getConnection());
		if (repo.addCheatsheet(sheet)) {
			response.sendRedirect("dashboard.jsp"); // အောင်မြင်ရင် dashboard ပြန်သွားမယ်
		}
	}
}
