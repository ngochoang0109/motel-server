package com.server.kltn.motel.api.user.payload;

import java.util.ArrayList;
import java.util.List;

public class NewsInfor {
	private long expense;
	private String title;
	private String	description;
	private List<String> videos= new ArrayList<>();
	private long totalAmount;
	
	public long getTypeOfPost() {
		return expense;
	}
	public void setTypeOfPost(long typeOfPost) {
		this.expense = typeOfPost;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<String> getVideos() {
		return videos;
	}
	public void setVideos(List<String> videos) {
		this.videos = videos;
	}
	public long getExpense() {
		return expense;
	}
	public void setExpense(long expense) {
		this.expense = expense;
	}
	public long getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}
}
