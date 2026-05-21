package com.library.model;

public class Title {
	private int title_id;
	private String title_name;

	// Default Constructor
	public Title() {
	}

	// Constructor with fields
	public Title(int title_id, String title_name) {
		this.title_id = title_id;
		this.title_name = title_name;
	}

	// Getter and Setter for title_id
	public int getTitle_id() {
		return title_id;
	}

	public void setTitle_id(int title_id) {
		this.title_id = title_id;
	}

	// Getter and Setter for title_name
	public String getTitle_name() {
		return title_name;
	}

	public void setTitle_name(String title_name) {
		this.title_name = title_name;
	}

	// Debug လုပ်ရလွယ်အောင် toString() လေးပါ ထည့်ပေးထားပါတယ်
	@Override
	public String toString() {
		return "Title [title_id=" + title_id + ", title_name=" + title_name + "]";
	}
}