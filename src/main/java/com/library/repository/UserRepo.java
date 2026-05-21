package com.library.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.library.model.User;

public class UserRepo {
	private Connection conn;

	public UserRepo(Connection conn) {
		this.conn = conn;
	}

	public boolean registerUser(User user) {
		boolean f = false;
		try {
			// Table name က 'users' ဖြစ်ရပါမယ်
			String query = "INSERT INTO user(username, email, password, role) VALUES(?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());

			// Role တန်ဖိုး null ဖြစ်မနေအောင် စစ်ဆေးခြင်း
			String role = (user.getRole() == null || user.getRole().isEmpty()) ? "User" : user.getRole();
			ps.setString(4, role);

			int i = ps.executeUpdate();
			if (i == 1) {
				f = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}

	public User login(String email, String password) {
		User user = null;
		try {
			String query = "SELECT * FROM user WHERE email=? AND password=?";
			PreparedStatement pst = this.conn.prepareStatement(query);
			pst.setString(1, email);
			pst.setString(2, password);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				user = new User();

				// သင့် Database ပုံအရ Column name သည် 'user_id' ဖြစ်သည်
				user.setId(rs.getInt("user_id"));

				user.setName(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));

				// Role သည် Admin/User ခွဲခြားရန် အရေးကြီးသည်
				user.setRole(rs.getString("role"));
			}
		} catch (Exception e) {
			System.out.println("Login Error: " + e.getMessage());
			e.printStackTrace();
		}
		return user;
	}
}