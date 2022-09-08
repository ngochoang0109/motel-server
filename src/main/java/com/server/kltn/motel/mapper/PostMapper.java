package com.server.kltn.motel.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.server.kltn.motel.api.user.payload.NewsCard;
import com.server.kltn.motel.entity.Image;
import com.server.kltn.motel.entity.Post;

@Component
public class PostMapper {
	public NewsCard mapPostToNewsCard(Post post) {
		String sourceImg="";
		for (Image img : post.getImages()) {
			if(img.getType().equals("avatar")) {
				sourceImg= img.getSource();
				break;
			}
		}

		NewsCard newsCard= new NewsCard(post.getId(),post.getUser().getPhone(), post.getUser().getFullname(), 
								post.getTitle(), post.getDescription(), sourceImg, 
								post.getAccomodation().getPrice(), post.getAccomodation().getArea(), 
								post.getAccomodation().getDicstrict(), post.getAccomodation().getProvince(), 
								post.getStartedDate().toString(), post.getClosedDate().toString());
		return newsCard;
	}
	
	public List<NewsCard> mapPostsToNewsCards(List<Post> posts){
		List<NewsCard> newsCards= new ArrayList<>();
		for (Post p : posts) {
			newsCards.add(this.mapPostToNewsCard(p));
		}
		return newsCards;
	}
}
