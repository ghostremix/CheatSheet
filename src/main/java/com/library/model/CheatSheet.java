package com.library.model;

import java.util.Date;

public class CheatSheet {
	private int sheetId;
	private String title;
	private String content;
	private Date createdAt;
	private Category category; // Category Table နဲ့ Relationship ချိတ်ဆက်ထားခြင်း

	// Default Constructor
	public CheatSheet() {
		super();
	}

	// Parameterized Constructor (Data အသစ်ထည့်တဲ့အခါ သုံးရလွယ်ကူစေရန်)
	public CheatSheet(int sheetId, String title, String content, Date createdAt, Category category) {
		super();
		this.sheetId = sheetId;
		this.title = title;
		this.content = content;
		this.createdAt = createdAt;
		this.category = category;
	}

	// --- Getters and Setters ---

	public int getSheetId() {
		return sheetId;
	}

	public void setSheetId(int sheetId) {
		this.sheetId = sheetId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}