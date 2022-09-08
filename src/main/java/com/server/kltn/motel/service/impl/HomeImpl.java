package com.server.kltn.motel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.server.kltn.motel.api.user.payload.NewsCard;
import com.server.kltn.motel.common.HandleDateCommon;
import com.server.kltn.motel.common.PageAndSortCommon;
import com.server.kltn.motel.entity.Post;
import com.server.kltn.motel.mapper.PostMapper;
import com.server.kltn.motel.page.Page;
import com.server.kltn.motel.repository.PostRepository;
import com.server.kltn.motel.service.HomeService;

@Service
public class HomeImpl implements HomeService{
	
	@Autowired
	private PageAndSortCommon pageAndSortCommon;
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private PostMapper postMapper;
	@Autowired
	private HandleDateCommon handleDateCommon;
	
	@Override
	public Page<NewsCard> getPageNewsCard(int pageNo, int pageSize, String field, int mode) {
		Pageable pageable= pageAndSortCommon.getPageable(pageNo, pageSize, field, mode);
		org.springframework.data.domain.Page<Post> posts= postRepository.getPosts(pageable, handleDateCommon.getCurrentDateTime());
		Page<NewsCard> paging= new Page<>(postMapper.mapPostsToNewsCards(posts.getContent()), 
				posts.getNumber(), posts.getSize(), posts.getTotalElements(), 
				posts.getTotalPages(), posts.isLast(), posts.isFirst());
		return paging;
	}
}
