package com.server.kltn.motel.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "expense")
public class Expense {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String type;
	private long cost;
	@OneToOne(mappedBy = "expense")
    private Post post;
	
	public Expense(long id, String type, long cost) {
		super();
		this.id = id;
		this.type = type;
		this.cost = cost;
	}
	@ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "exp_dis", 
        joinColumns = {@JoinColumn(name="exp_id")},
        inverseJoinColumns = {@JoinColumn(name="dis_id")}
    )
    List<Discount> discounts = new ArrayList<>();
	
	public Expense() {
		super();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getCost() {
		return cost;
	}
	public void setCost(long cost) {
		this.cost = cost;
	}
	
}
