package com.server.kltn.motel.api.user.payload;

public class UpdateItemCart {
	private long id;
	private boolean checked;
	private int numDate;
	private String startedDate;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public int getNumDate() {
		return numDate;
	}
	public void setNumDate(int numDate) {
		this.numDate = numDate;
	}
	public String getStartedDate() {
		return startedDate;
	}
	public void setStartedDate(String startedDate) {
		this.startedDate = startedDate;
	}
}
