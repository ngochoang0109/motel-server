package com.server.kltn.motel.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class Image {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String source;
	private String type;
	
	public Image(String source, String type) {
		super();
		this.source = source;
		this.type = type;
	}
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="post_id")
	private Post post;
	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
	@OneToOne(mappedBy = "avatar")
	private User user;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}