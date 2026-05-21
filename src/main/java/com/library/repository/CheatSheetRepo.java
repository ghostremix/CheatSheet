package com.library.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.library.model.CheatSheet;
import com.library.model.Category;

public class CheatSheetRepo {
	private Connection conn;

	public CheatSheetRepo(Connection conn) {
		this.conn = conn;
	}

	// Cheat Sheet အသစ်ထည့်ရန်
	public boolean addCheatsheet(CheatSheet sheet) {
		boolean f = false;
		try {
			String query = "INSERT INTO cheatsheet(title, content, Category_category_id) VALUES(?,?,?)";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, sheet.getTitle());
			ps.setString(2, sheet.getContent());
			ps.setInt(3, sheet.getCategory().getCategoryId());

			int i = ps.executeUpdate();
			if (i == 1) {
				f = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}

	// Cheat Sheet အားလုံးကို ပြန်ထုတ်ရန်
	public List<CheatSheet> getAllCheatsheets() {
		List<CheatSheet> list = new ArrayList<>();
		try {
			String query = "SELECT * FROM cheatsheet";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				CheatSheet sheet = new CheatSheet(); // S အကြီးဖြင့် ပြင်ထားသည်
				sheet.setSheetId(rs.getInt("sheet_id"));
				sheet.setTitle(rs.getString("title"));
				sheet.setContent(rs.getString("content"));
				sheet.setCreatedAt(rs.getTimestamp("created_at"));

				Category cat = new Category();
				cat.setCategoryId(rs.getInt("Category_category_id"));
				sheet.setCategory(cat);

				list.add(sheet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// Cheat Sheet ကို ပြန်ပြင်ရန် (Update)
	public boolean updateCheatsheet(CheatSheet sheet) {
		boolean f = false;
		try {
			String query = "UPDATE cheatsheet SET title=?, content=?, Category_category_id=? WHERE sheet_id=?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, sheet.getTitle());
			ps.setString(2, sheet.getContent());
			ps.setInt(3, sheet.getCategory().getCategoryId());
			ps.setInt(4, sheet.getSheetId());

			int i = ps.executeUpdate();
			if (i == 1) {
				f = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}

	// Cheat Sheet ကို ဖျက်ရန် (Delete)
	public boolean deleteCheatsheet(int id) {
		boolean f = false;
		try {
			String query = "DELETE FROM cheatsheet WHERE sheet_id=?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id);

			int i = ps.executeUpdate();
			if (i == 1) {
				f = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}

	// Category အလိုက် Cheat Sheets များကို ရှာရန်
	public List<CheatSheet> getCheatsheetsByCategory(int categoryId) {
		List<CheatSheet> list = new ArrayList<>();
		try {
			String query = "SELECT * FROM cheatsheet WHERE Category_category_id=?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, categoryId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				CheatSheet sheet = new CheatSheet(); // S အကြီးဖြင့် ပြင်ထားသည်
				sheet.setSheetId(rs.getInt("sheet_id"));
				sheet.setTitle(rs.getString("title"));
				sheet.setContent(rs.getString("content"));
				sheet.setCreatedAt(rs.getTimestamp("created_at"));

				Category cat = new Category();
				cat.setCategoryId(rs.getInt("Category_category_id"));
				sheet.setCategory(cat);

				list.add(sheet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// ID တစ်ခုတည်းဖြင့် Cheat Sheet အချက်အလက်ကို ပြန်ထုတ်ရန်
	public CheatSheet getCheatsheetById(int id) {
		CheatSheet sheet = null;
		try {
			String query = "SELECT * FROM cheatsheet WHERE sheet_id=?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				sheet = new CheatSheet();
				sheet.setSheetId(rs.getInt("sheet_id"));
				sheet.setTitle(rs.getString("title"));
				sheet.setContent(rs.getString("content"));
				sheet.setCreatedAt(rs.getTimestamp("created_at"));

				Category cat = new Category();
				cat.setCategoryId(rs.getInt("Category_category_id"));
				sheet.setCategory(cat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sheet;
	}

	public List<CheatSheet> searchEverything(String keyword) {
		List<CheatSheet> list = new ArrayList<>();
		try {
			// Content ထဲက စာလုံးတွေကို လိုက်မရှာတော့ဘဲ Snippet Title ခေါင်းစဉ်ထဲမှာ ပါမှသာ
			// တိကျစွာ ထုတ်ပေးမည်
			String sql = "SELECT cs.*, cat.category_name " + "FROM cheatsheet cs "
					+ "JOIN category cat ON cs.Category_category_id = cat.category_id " + "WHERE cs.title LIKE ?";

			PreparedStatement ps = conn.prepareStatement(sql);
			String searchKey = "%" + keyword + "%";
			ps.setString(1, searchKey);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				CheatSheet s = new CheatSheet();
				s.setSheetId(rs.getInt("sheet_id"));
				s.setTitle(rs.getString("title"));
				s.setContent(rs.getString("content"));

				Category cat = new Category();
				cat.setCategoryId(rs.getInt("Category_category_id"));
				cat.setCategoryName(rs.getString("category_name"));
				s.setCategory(cat);

				list.add(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}