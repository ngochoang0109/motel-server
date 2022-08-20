package com.server.kltn.motel.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "post")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String title;
	private String description;
	
	/*
	 0: wait for approved
	 1: approved
	 2: rejected
	 3: closed*/
	private int status;
	private LocalDateTime createdDate; 
	/*
	 * user set post is showed at date*/
	private LocalDateTime startedDate;
	private LocalDateTime closedDate;
	private LocalDateTime updatedDate;
	private LocalDateTime approvedDate;
	private boolean rePost;
	
	@OneToMany(mappedBy = "post")
	List<Image> images= new ArrayList<>();
	@OneToMany(mappedBy = "post")
	List<Video> videos= new ArrayList<>();
	@OneToOne
    @JoinColumn(name="expense_id")
    private Expense expense;
	@OneToOne
    @JoinColumn(name="accomodation_id")
    private Accomodation accomodation;
	@ManyToOne
    @JoinColumn(name="user_id")
	private User user;
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
	public boolean isRePost() {
		return rePost;
	}
	public void setRePost(boolean rePost) {
		this.rePost = rePost;
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
	
}
