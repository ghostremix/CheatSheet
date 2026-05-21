package com.library.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.library.config.DBConnect;
import com.library.model.User;
import com.library.repository.UserRepo;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public RegisterServlet() {
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

		// doPost ထဲမှာ ရေးရန်
		String name = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		// Model ထဲ Data ထည့်ခြင်း
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		user.setRole("USER"); // Default role

		// Repo သုံးပြီး DB ထဲ သွင်းခြင်း
		UserRepo repo = new UserRepo(DBConnect.getConnection());
		boolean success = repo.registerUser(user);

		if (success) {
			response.sendRedirect("login.jsp"); // အောင်မြင်ရင် login သွားမယ်
		} else {
			// မအောင်မြင်ရင် error ပြန်ပြဖို့ logic ရေးနိုင်ပါတယ်
			System.out.println("Error: Registration failed.");
		}

	}
}
