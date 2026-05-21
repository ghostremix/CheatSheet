package com.library.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.library.config.DBConnect;
import com.library.model.User;
import com.library.repository.UserRepo;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
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

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		HttpSession session = request.getSession();
		UserRepo repo = new UserRepo(DBConnect.getConnection());

		User user = repo.login(email, password);

		if (user != null) {
			// ၁။ Login အောင်မြင်ရင် user object တစ်ခုလုံး သိမ်းမယ်
			session.setAttribute("userObj", user);

			// ၂။ Role ကို session ထဲမှာ သီးသန့်သိမ်းထားမယ် (Dashboard က JavaScript မှာ
			// သုံးဖို့)
			session.setAttribute("role", user.getRole().toLowerCase()); // e.g., "admin" or "user"

			// ၃။ Role အပေါ်မူတည်ပြီး လမ်းကြောင်းခွဲမယ်
			if ("ADMIN".equalsIgnoreCase(user.getRole())) {
				// Admin ဆိုရင် Dashboard (CRUD ပါတဲ့နေရာ) ကို ပို့မယ်
				response.sendRedirect("dashboard.jsp");
			} else {
				// User ဆိုရင် ပုံမှန် Dashboard သို့မဟုတ် Home ကို ပို့မယ်
				response.sendRedirect("dashboard.jsp");
			}
		} else {
			// Login မအောင်မြင်ရင်
			session.setAttribute("errorMsg", "Invalid Email or Password");
			response.sendRedirect("login.jsp");
		}
	}
}