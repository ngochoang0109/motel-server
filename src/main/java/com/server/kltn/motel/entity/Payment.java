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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "payment")
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
    @JoinColumn(name="user_id")
	private User user;

	@OneToMany(mappedBy = "payment",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<PaymentDetail> paymentDetails = new ArrayList<>();
	
	private long totalAmount;
	private LocalDateTime createdDate;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public List<PaymentDetail> getPaymentDetails() {
		return paymentDetails;
	}
	public void setPaymentDetails(List<PaymentDetail> paymentDetails) {
		this.paymentDetails = paymentDetails;
	}
	public long getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
