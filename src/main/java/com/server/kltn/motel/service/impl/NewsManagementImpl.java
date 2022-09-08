package com.server.kltn.motel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.server.kltn.motel.api.user.payload.NewsCard;
import com.server.kltn.motel.common.HandleDateCommon;
import com.server.kltn.motel.common.PageAndSortCommon;
import com.server.kltn.motel.constant.NewsModeConstant;
import com.server.kltn.motel.entity.Post;
import com.server.kltn.motel.mapper.PostMapper;
import com.server.kltn.motel.page.Page;
import com.server.kltn.motel.repository.PostRepository;
import com.server.kltn.motel.service.NewsManagementService;

@Service
public class NewsManagementImpl implements NewsManagementService {
	@Autowired
	private PageAndSortCommon pageAndSortCommon;
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private PostMapper postMapper;
	@Autowired
	private HandleDateCommon handleDateCommon;

	@Override
	public Page<NewsCard> getAllNewsOfUser(int pageNo, int pageSize, String field, int mode, String username) {
		Pageable pageable = pageAndSortCommon.getPageable(pageNo, pageSize, field, mode);
		org.springframework.data.domain.Page<Post> page = postRepository.getNewsOfUser(pageable, username);
		List<NewsCard> newsCards = postMapper.mapPostsToNewsCards(page.getContent());
		for (int i = 0; i < page.getContent().size(); i++) {
			Post post = page.getContent().get(i);
			if (post.getStatus() == 0) {
				newsCards.get(i).setMode(NewsModeConstant.WAITING_APROVED);
			} else if (post.getStatus() == 1) {
				if (!post.isPayment()) {
					newsCards.get(i).setTotalAmount(post.getTotalAmount());
					newsCards.get(i).setMode(NewsModeConstant.NEWS_WAIT_PAYMENT);
				} else {
					if (post.getStartedDate().compareTo(handleDateCommon.getCurrentDateTime()) > 0) {
						newsCards.get(i).setMode(NewsModeConstant.WAITING_SHOW);
					} else if (post.getStartedDate().compareTo(handleDateCommon.getCurrentDateTime()) <= 0
							&& post.getClosedDate().compareTo(handleDateCommon.getCurrentDateTime()) >= 0) {
						newsCards.get(i).setMode(NewsModeConstant.SHOWING);
					} else if(post.getClosedDate().compareTo(handleDateCommon.getCurrentDateTime()) < 0){
						newsCards.get(i).setMode(NewsModeConstant.EXPRIED);
					}
				}
			} else if (post.getStatus() == 2) {
				newsCards.get(i).setMode(NewsModeConstant.NEWS_REJECT);
			} else if (post.getStatus() == 3) {
				newsCards.get(i).setMode(NewsModeConstant.HINDDEN);
			}
		}
		Page<NewsCard> paging = new Page<>(newsCards, page.getNumber(), page.getSize(), page.getTotalElements(),
				page.getTotalPages(), page.isLast(), page.isFirst());

		return paging;
	}
	
	@Override
	public Page<NewsCard> getNewsWaitingAproved(int pageNo, int pageSize, String field, int mode, String username) {
		Pageable pageable = pageAndSortCommon.getPageable(pageNo, pageSize, field, mode);
		org.springframework.data.domain.Page<Post> page = postRepository.getNewsWaittingAprovedOfUser(pageable, username);
		List<NewsCard> newsCards = postMapper.mapPostsToNewsCards(page.getContent());
		for (int i = 0; i < page.getContent().size(); i++) {
			newsCards.get(i).setMode(NewsModeConstant.WAITING_APROVED);
		}
		Page<NewsCard> paging = new Page<>(newsCards, page.getNumber(), page.getSize(), page.getTotalElements(),
				page.getTotalPages(), page.isLast(), page.isFirst());
		return paging;
	}
	
	@Override
	public Page<NewsCard> getNewsRejectOfUser(int pageNo, int pageSize, String field, int mode, String username) {
		Pageable pageable = pageAndSortCommon.getPageable(pageNo, pageSize, field, mode);
		org.springframework.data.domain.Page<Post> page = postRepository.getNewsRejectOfUser(pageable, username);
		List<NewsCard> newsCards = postMapper.mapPostsToNewsCards(page.getContent());
		for (int i = 0; i < page.getContent().size(); i++) {
			newsCards.get(i).setMode(NewsModeConstant.NEWS_REJECT);
		}
		Page<NewsCard> paging = new Page<>(newsCards, page.getNumber(), page.getSize(), page.getTotalElements(),
				page.getTotalPages(), page.isLast(), page.isFirst());
		return paging;
	}
	
	@Override
	public Page<NewsCard> getDontPaymentOfUser(int pageNo, int pageSize, String field, int mode, String username) {
		Pageable pageable = pageAndSortCommon.getPageable(pageNo, pageSize, field, mode);
		org.springframework.data.domain.Page<Post> page = postRepository.getDontPaymentOfUser(pageable, username);
		List<NewsCard> newsCards = postMapper.mapPostsToNewsCards(page.getContent());
		for (int i = 0; i < page.getContent().size(); i++) {
			newsCards.get(i).setMode(NewsModeConstant.NEWS_WAIT_PAYMENT);
		}
		Page<NewsCard> paging = new Page<>(newsCards, page.getNumber(), page.getSize(), page.getTotalElements(),
				page.getTotalPages(), page.isLast(), page.isFirst());
		return paging;
	}
	
	@Override
	public Page<NewsCard> getWaittingShowOfUser(int pageNo, int pageSize, String field, int mode, String username) {
		Pageable pageable = pageAndSortCommon.getPageable(pageNo, pageSize, field, mode);
		org.springframework.data.domain.Page<Post> page = postRepository.getWaittingShowOfUser(pageable, username, handleDateCommon.getCurrentDateTime());
		List<NewsCard> newsCards = postMapper.mapPostsToNewsCards(page.getContent());
		for (int i = 0; i < page.getContent().size(); i++) {
			newsCards.get(i).setMode(NewsModeConstant.WAITING_SHOW);
		}
		Page<NewsCard> paging = new Page<>(newsCards, page.getNumber(), page.getSize(), page.getTotalElements(),
				page.getTotalPages(), page.isLast(), page.isFirst());
		return paging;
	}
	
	@Override
	public Page<NewsCard> getNewsShowingOfUser(int pageNo, int pageSize, String field, int mode, String username) {
		Pageable pageable = pageAndSortCommon.getPageable(pageNo, pageSize, field, mode);
		org.springframework.data.domain.Page<Post> page = postRepository.getNewsShowingOfUser(pageable, username, handleDateCommon.getCurrentDateTime());
		List<NewsCard> newsCards = postMapper.mapPostsToNewsCards(page.getContent());
		for (int i = 0; i < page.getContent().size(); i++) {
			newsCards.get(i).setMode(NewsModeConstant.SHOWING);
		}
		Page<NewsCard> paging = new Page<>(newsCards, page.getNumber(), page.getSize(), page.getTotalElements(),
				page.getTotalPages(), page.isLast(), page.isFirst());
		return paging;
	}
	
	@Override
	public Page<NewsCard> getNewsExpriedOfUser(int pageNo, int pageSize, String field, int mode, String username) {
		Pageable pageable = pageAndSortCommon.getPageable(pageNo, pageSize, field, mode);
		org.springframework.data.domain.Page<Post> page = postRepository.getNewsExpriedOfUser(pageable, username, handleDateCommon.getCurrentDateTime());
		List<NewsCard> newsCards = postMapper.mapPostsToNewsCards(page.getContent());
		for (int i = 0; i < page.getContent().size(); i++) {
			newsCards.get(i).setMode(NewsModeConstant.EXPRIED);
		}
		Page<NewsCard> paging = new Page<>(newsCards, page.getNumber(), page.getSize(), page.getTotalElements(),
				page.getTotalPages(), page.isLast(), page.isFirst());
		return paging;
	}
	
	@Override
	public Page<NewsCard> getNewsHiddenOfUser(int pageNo, int pageSize, String field, int mode, String username) {
		Pageable pageable = pageAndSortCommon.getPageable(pageNo, pageSize, field, mode);
		org.springframework.data.domain.Page<Post> page = postRepository.getNewsHiddenOfUser(pageable, username);
		List<NewsCard> newsCards = postMapper.mapPostsToNewsCards(page.getContent());
		for (int i = 0; i < page.getContent().size(); i++) {
			newsCards.get(i).setMode(NewsModeConstant.HINDDEN);
		}
		Page<NewsCard> paging = new Page<>(newsCards, page.getNumber(), page.getSize(), page.getTotalElements(),
				page.getTotalPages(), page.isLast(), page.isFirst());
		return paging;
	}
}