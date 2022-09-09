package com.server.kltn.motel.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "discount")
public class Discount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String code;
	private int percent;
	private long price;
	private String description;
	private LocalDateTime startedDate;
	private LocalDateTime endDate;
	
	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinTable(
        name = "exp_dis", 
        joinColumns = {@JoinColumn(name="dis_id")},
        inverseJoinColumns = {@JoinColumn(name="exp_id")}
    )
    private List<Expense> expenses = new ArrayList<>();
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getPercent() {
		return percent;
	}
	public void setPercent(int percent) {
		this.percent = percent;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public List<Expense> getExpenses() {
		return expenses;
	}
	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
	}
	public LocalDateTime getStartedDate() {
		return startedDate;
	}
	public void setStartedDate(LocalDateTime startedDate) {
		this.startedDate = startedDate;
	}
	public LocalDateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
}
