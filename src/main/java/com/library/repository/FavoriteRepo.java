package com.library.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.library.model.CheatSheet;
import com.library.model.Category;

public class FavoriteRepo {
	private Connection conn;

	public FavoriteRepo(Connection conn) {
		this.conn = conn;
	}

	// Favorite အသစ်ထည့်ရန် (Save)
	public boolean addFavorite(int userId, int sheetId) {
		boolean f = false;
		try {
			// အရင်ရှိပြီးသား ဟုတ်မဟုတ် စစ်တာ (Duplicate မဖြစ်အောင်)
			if (!isAlreadyFavorite(userId, sheetId)) {
				String sql = "INSERT INTO favorite (User_user_id, CheatSheet_sheet_id) VALUES (?, ?)";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, userId);
				ps.setInt(2, sheetId);
				int i = ps.executeUpdate();
				if (i == 1)
					f = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}

	// Favorite ပြန်ဖျက်ရန် (Unsave)
	public boolean removeFavorite(int userId, int sheetId) {
		boolean f = false;
		try {
			String sql = "DELETE FROM favorite WHERE User_user_id = ? AND CheatSheet_sheet_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, sheetId);
			int i = ps.executeUpdate();
			if (i == 1)
				f = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}

	// ရှိပြီးသား ဟုတ်မဟုတ် စစ်ဆေးရန်
	public boolean isAlreadyFavorite(int userId, int sheetId) {
		boolean exists = false;
		try {
			String sql = "SELECT * FROM favorite WHERE User_user_id = ? AND CheatSheet_sheet_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, sheetId);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				exists = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exists;
	}

	// User တစ်ယောက် သိမ်းထားသမျှ Snippets အားလုံးကို ဆွဲထုတ်ရန်
	public List<CheatSheet> getSavedSnippets(int userId) {
		List<CheatSheet> list = new ArrayList<>();
		try {
			// favorite table နဲ့ cheatsheet table ကို Join တွဲပြီး ဆွဲထုတ်မယ်
			String sql = "SELECT c.* FROM cheatsheet c " + "JOIN favorite f ON c.sheet_id = f.CheatSheet_sheet_id "
					+ "WHERE f.User_user_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				CheatSheet s = new CheatSheet();
				s.setSheetId(rs.getInt("sheet_id"));
				s.setTitle(rs.getString("title"));
				s.setContent(rs.getString("content"));

				// Category object လိုအပ်ရင် (Dummy သို့မဟုတ် Category Repo ကနေ ယူနိုင်သည်)
				Category cat = new Category();
				cat.setCategoryId(rs.getInt("Category_category_id"));
				s.setCategory(cat);

				list.add(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}