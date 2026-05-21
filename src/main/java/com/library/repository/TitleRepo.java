package com.library.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.library.model.Title;

public class TitleRepo {
	private Connection conn;

	public TitleRepo(Connection conn) {
		this.conn = conn;
	}

	// ၁။ Title အသစ်ထည့်ရန် (ဥပမာ- AI, Programming)
	public boolean addTitle(String titleName) {
		boolean f = false;
		try {
			String sql = "INSERT INTO title(title_name) VALUES(?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, titleName);

			if (ps.executeUpdate() == 1) {
				f = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}

	// ၂။ Title အားလုံးကို ပြန်ထုတ်ရန်
	public List<Title> getAllTitles() {
		List<Title> list = new ArrayList<>();
		try {
			// SQL query ၏ အဆုံးတွင် ORDER BY title_name ASC ကို ထည့်သွင်းလိုက်ခြင်းဖြင့် A
			// to Z အလိုအလျောက် စီပေးမည်ဖြစ်သည်
			String query = "SELECT * FROM title ORDER BY title_name ASC";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Title t = new Title();
				t.setTitle_id(rs.getInt("title_id"));
				t.setTitle_name(rs.getString("title_name"));
				// တခြား field တွေ ရှိရင်လည်း ဒီမှာ ဆက်ထည့်ပါ

				list.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// ၃။ Title တစ်ခုတည်းကို ID ဖြင့် ရှာရန်
	public Title getTitleById(int id) {
		Title t = null;
		try {
			String sql = "SELECT * FROM title WHERE title_id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				t = new Title();
				t.setTitle_id(rs.getInt("title_id"));
				t.setTitle_name(rs.getString("title_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	// ၄။ Title အမည် ပြန်ပြင်ရန်
	public boolean updateTitle(Title t) {
		boolean f = false;
		try {
			String sql = "UPDATE title SET title_name=? WHERE title_id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, t.getTitle_name());
			ps.setInt(2, t.getTitle_id());

			if (ps.executeUpdate() == 1) {
				f = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}

	// ၅။ Title ကို ဖျက်ရန် (အောက်က Category တွေပါ တစ်ခါတည်း ပျက်သွားပါလိမ့်မည်)
	public boolean deleteTitle(int id) {
		boolean f = false;
		try {
			// SQL မှာ ON DELETE CASCADE လုပ်ထားလျှင် Category တွေကို manually ဖျက်ရန်
			// မလိုပါ
			String sql = "DELETE FROM title WHERE title_id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);

			if (ps.executeUpdate() == 1) {
				f = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}
}