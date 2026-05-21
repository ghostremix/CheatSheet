package com.library.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.library.model.Category;

public class CategoryRepo {
	private Connection conn;

	public CategoryRepo(Connection conn) {
		this.conn = conn;
	}

	// ၁။ Category အသစ်ထည့်ရန် (title_id နဲ့ ချိတ်မည်)
	public boolean addCategory(Category cat) {
		boolean f = false;
		try {
			// image_955a3b.png အရ category table တွင် category_name နှင့် title_id
			// သာရှိတော့သည်
			String sql = "insert into category(category_name, title_id) values(?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, cat.getCategoryName());
			ps.setInt(2, cat.getTitleId()); // title_id ကို FK အဖြစ်ထည့်မည်

			if (ps.executeUpdate() == 1)
				f = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}

	// ၂။ Category အားလုံးကို ပြန်ထုတ်ရန်
	public List<Category> getAllCategories() {
		List<Category> list = new ArrayList<>();
		try {
			// category_name ကို A ကနေ Z အတိုင်း စီခိုင်းတာ ဖြစ်ပါတယ်
			String query = "SELECT * FROM category ORDER BY category_name ASC";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Category cat = new Category();
				cat.setCategoryId(rs.getInt("category_id"));
				cat.setCategoryName(rs.getString("category_name"));
				cat.setTitleId(rs.getInt("title_id"));
				list.add(cat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// ၃။ Category ပြန်ပြင်ရန်
	public boolean updateCategory(Category cat) {
		boolean f = false;
		try {
			// title_id ကို query ထဲက ဖယ်လိုက်ပါ
			String sql = "UPDATE category SET category_name=? WHERE category_id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, cat.getCategoryName());
			ps.setInt(2, cat.getCategoryId());

			int i = ps.executeUpdate();
			if (i == 1) {
				f = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}

	// ၄။ Category ကို ဖျက်ရန်
	public boolean deleteCategory(int id) {
		boolean f = false;
		try {
			// image_955a3b.png အရ cheatsheet table ထဲက FK နာမည်ကို သတိထားပါ
			String sql = "DELETE FROM cheatsheet WHERE Category_category_id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();

			String sql2 = "DELETE FROM category WHERE category_id=?";
			PreparedStatement ps2 = conn.prepareStatement(sql2);
			ps2.setInt(1, id);

			if (ps2.executeUpdate() == 1)
				f = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}
}