package com.library.model;

public class Category {
	private int categoryId;
	private String categoryName;
	private int titleId; // Database ထဲက title_id နဲ့ ချိတ်ဖို့

	// Default Constructor
	public Category() {
		super();
	}

	// Parameterized Constructor
	public Category(int categoryId, String categoryName, int titleId) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.titleId = titleId;
	}

	// --- Getters and Setters ---

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	// titleId အတွက် Getter နှင့် Setter (ဒါမှ Repo က ခေါ်သုံးလို့ရမှာပါ)
	public int getTitleId() {
		return titleId;
	}

	public void setTitleId(int titleId) {
		this.titleId = titleId;
	}

	@Override
	public String toString() {
		return "Category [categoryId=" + categoryId + ", categoryName=" + categoryName + ", titleId=" + titleId + "]";
	}
}