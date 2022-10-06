package com.server.kltn.motel.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.server.kltn.motel.api.user.payload.CountNews;
import com.server.kltn.motel.api.user.payload.NewsCard;
import com.server.kltn.motel.api.user.payload.SearchParam;
import com.server.kltn.motel.common.HandleDateCommon;
import com.server.kltn.motel.common.PageAndSortCommon;
import com.server.kltn.motel.entity.Post;
import com.server.kltn.motel.mapper.PostMapper;
import com.server.kltn.motel.page.Page;
import com.server.kltn.motel.repository.AccomodationRepo;
import com.server.kltn.motel.repository.PostRepository;
import com.server.kltn.motel.sepecification.SearchSepecification;
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
	
	@Autowired
	private SearchSepecification searchSepecification;
	
	private StringBuilder sb= new StringBuilder("accomodation.");
	
	@Autowired
	private AccomodationRepo accomodationRepo;
	
	@Override
	public Page<NewsCard> getPageNewsCard(int pageNo, int pageSize, String field, int mode) {
		Pageable pageable= pageAndSortCommon.getPageable(pageNo, pageSize, field, mode);
		org.springframework.data.domain.Page<Post> posts= postRepository.getPosts(pageable, 
				handleDateCommon.getCurrentDateTime());
		List<Post> postSorted= sortPriorityAndDate(posts.getContent());
		Page<NewsCard> paging= new Page<>(postMapper.mapPostsToNewsCards(postSorted), 
				posts.getNumber(), posts.getSize(), posts.getTotalElements(), 
				posts.getTotalPages(), posts.isLast(), posts.isFirst());
		return paging;
	}
	
	@Override
	public Page<NewsCard> getPageNewsCardByType(int pageNo, int pageSize, String field, int mode, long type) {
		Pageable pageable= pageAndSortCommon.getPageable(pageNo, pageSize, field, mode);
		org.springframework.data.domain.Page<Post> posts= postRepository.getPostsByType(pageable, 
				handleDateCommon.getCurrentDateTime(), type);
		
		List<Post> postSorted= sortPriorityAndDate(posts.getContent());
		Page<NewsCard> paging= new Page<>(postMapper.mapPostsToNewsCards(postSorted), 
				posts.getNumber(), posts.getSize(), posts.getTotalElements(), 
				posts.getTotalPages(), posts.isLast(), posts.isFirst());
		return paging;
	}
	
	@Override
	public List<Post> sortPriorityAndDate(List<Post> posts) {
		Comparator<Post> priority=(post1, post2)->{
			return Long.compare(post2.getExpense().getCost(), post1.getExpense().getCost());
		};
//		Comparator<Post> sortDate=(post1, post2)->{
//			return post1.getStartedDate().compareTo(post2.getStartedDate());
//		};
//		sortDate.thenComparing(priority)
		List<Post> postSorted= posts.stream()
								.sorted(priority)
								.collect(Collectors.toList());
		return postSorted;
	}
	
	@Override
	public Page<NewsCard> searchPageCard(int pageNo, int pageSize, String field, int mode, SearchParam searchParam) {
		if(field== "price" || field == "area") {
			sb.append(field);
		}else {
			sb.setLength(0);
			sb.append(field);
		}
		Pageable pageable= pageAndSortCommon.getPageable(pageNo, pageSize, sb.toString(), mode);
		searchSepecification.searchNewsByParamQuery(searchParam,handleDateCommon.getCurrentDateTime());
		org.springframework.data.domain.Page<Post> posts= postRepository.findAll(searchSepecification.searchNewsByParamQuery(searchParam, handleDateCommon.getCurrentDateTime()),pageable);
		
		List<Post> postSorted= sortPriorityAndDate(posts.getContent());
		Page<NewsCard> paging= new Page<>(postMapper.mapPostsToNewsCards(postSorted), 
				posts.getNumber(), posts.getSize(), posts.getTotalElements(), 
				posts.getTotalPages(), posts.isLast(), posts.isFirst());
		return paging;
	}
	
	@Override
	public List<CountNews> getCountByProvince() {
		List<CountNews> getCount= accomodationRepo.countByProvince(handleDateCommon.getCurrentDateTime());
		return getCount;
	}
}
