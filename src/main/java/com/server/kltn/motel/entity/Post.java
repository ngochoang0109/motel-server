package com.server.kltn.motel.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "post")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String title;
	@Lob
	@Column(length =100000)
	private String description;
	@Lob
	@Column(length =100000)
	private String reason;
	/*
	 * 0: wait for approved 1: approved 2: rejected 3: closed
	 */
	private int status;
	private LocalDateTime createdDate;
	/*
	 * user set post is showed at date
	 */
	private LocalDateTime startedDate;
	private LocalDateTime closedDate;
	private LocalDateTime updatedDate;
	private LocalDateTime approvedDate;
	private boolean isPayment;
	private long totalAmount;

	public long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}

	@OneToMany(mappedBy = "post",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<Image> images = new ArrayList<>();
	@OneToMany(mappedBy = "post",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<Video> videos = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "expense_id")
	private Expense expense;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "discount_id")
	private Discount discount;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "accomodation_id")
	private Accomodation accomodation;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "post",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<CartDetail> cartDetails = new ArrayList<>();

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setPayment(boolean isPayment) {
		this.isPayment = isPayment;
	}

	public LocalDateTime getStartedDate() {
		return startedDate;
	}

	public void setStartedDate(LocalDateTime startedDate) {
		this.startedDate = startedDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(LocalDateTime closedDate) {
		this.closedDate = closedDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public LocalDateTime getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(LocalDateTime approvedDate) {
		this.approvedDate = approvedDate;
	}

	public boolean isPayment() {
		return isPayment;
	}

	public void setIsPayment(boolean isPayment) {
		this.isPayment = isPayment;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public List<Video> getVideos() {
		return videos;
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}

	public Post() {
		super();
	}

	public Post(String title, String description, List<Video> videos) {
		super();
		this.title = title;
		this.description = description;
		this.videos = videos;
	}

	public Expense getExpense() {
		return expense;
	}

	public void setExpense(Expense expense) {
		this.expense = expense;
	}

	public Accomodation getAccomodation() {
		return accomodation;
	}

	public void setAccomodation(Accomodation accomodation) {
		this.accomodation = accomodation;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Discount getDiscount() {
		return discount;
	}

	public void setDiscount(Discount discount) {
		this.discount = discount;
	}
}
