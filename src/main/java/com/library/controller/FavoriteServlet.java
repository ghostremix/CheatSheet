package com.library.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.library.config.DBConnect;
import com.library.model.User;
import com.library.repository.FavoriteRepo;

/**
 * Servlet implementation class FavoriteServlet
 */
@WebServlet("/FavoriteServlet")
public class FavoriteServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();

		try {
			int sheetId = Integer.parseInt(request.getParameter("id"));
			String action = request.getParameter("action");
			User user = (User) request.getSession().getAttribute("userObj");

			if (user == null) {
				out.print("{\"success\": false, \"message\": \"Not logged in\"}");
				return;
			}

			// Database Logic (FavoriteRepo ထဲမှာ ရေးထားတာကို ခေါ်သုံးပါ)
			FavoriteRepo repo = new FavoriteRepo(DBConnect.getConnection());
			boolean result;
			if ("add".equals(action)) {
				result = repo.addFavorite(user.getId(), sheetId);
			} else {
				result = repo.removeFavorite(user.getId(), sheetId);
			}

			if (result) {
				out.print("{\"success\": true}");
			} else {
				out.print("{\"success\": false}");
			}
		} catch (Exception e) {
			out.print("{\"success\": false, \"error\": \"" + e.getMessage() + "\"}");
		}
	}
}