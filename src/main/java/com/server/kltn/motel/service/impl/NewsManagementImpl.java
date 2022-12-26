package com.server.kltn.motel.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.server.kltn.motel.api.admin.payload.RejectDatasource;
import com.server.kltn.motel.api.user.payload.FilterParam;
import com.server.kltn.motel.api.user.payload.NewsCard;
import com.server.kltn.motel.common.HandleDateCommon;
import com.server.kltn.motel.common.PageAndSortCommon;
import com.server.kltn.motel.constant.NewsModeConstant;
import com.server.kltn.motel.entity.Post;
import com.server.kltn.motel.exception.BadRequestException;
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
	public Page<NewsCard> getAllNewsOfUser(int pageNo, int pageSize, String field, int mode, 
											String username, FilterParam filterParam) {
		Pageable pageable = pageAndSortCommon.getPageable(pageNo, pageSize, field, mode);
		org.springframework.data.domain.Page<Post> page = postRepository.getNewsOfUser(
				pageable, username, filterParam.getTextSearch(), filterParam.getStartedDate(), 
				filterParam.getClosedDate(),
				filterParam.getTypeOfAcc() ,filterParam.getTypeOfNews(),
				filterParam.getProvince(), filterParam.getDistrict());
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
					} else if (post.getClosedDate().compareTo(handleDateCommon.getCurrentDateTime()) < 0) {
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
	public Page<NewsCard> getNewsWaitingAproved(int pageNo, int pageSize, String field, 
			int mode, String username, FilterParam filterParam) {
		Pageable pageable = pageAndSortCommon.getPageable(pageNo, pageSize, field, mode);
		org.springframework.data.domain.Page<Post> page = postRepository.getNewsWaittingAprovedOfUser(pageable,
				username,
				filterParam.getTextSearch(), filterParam.getStartedDate(), 
				filterParam.getClosedDate(),
				filterParam.getTypeOfAcc() ,filterParam.getTypeOfNews(),
				filterParam.getProvince(), filterParam.getDistrict());
		List<NewsCard> newsCards = postMapper.mapPostsToNewsCards(page.getContent());
		for (int i = 0; i < page.getContent().size(); i++) {
			newsCards.get(i).setMode(NewsModeConstant.WAITING_APROVED);
		}
		Page<NewsCard> paging = new Page<>(newsCards, page.getNumber(), page.getSize(), page.getTotalElements(),
				page.getTotalPages(), page.isLast(), page.isFirst());
		return paging;
	}

	@Override
	public Page<NewsCard> getNewsRejectOfUser(int pageNo, int pageSize, String field, 
			int mode, String username, FilterParam filterParam) {
		Pageable pageable = pageAndSortCommon.getPageable(pageNo, pageSize, field, mode);
		org.springframework.data.domain.Page<Post> page = postRepository.getNewsRejectOfUser(pageable, 
				username, filterParam.getTextSearch(), filterParam.getStartedDate(), 
				filterParam.getClosedDate(),
				filterParam.getTypeOfAcc() ,filterParam.getTypeOfNews(),
				filterParam.getProvince(), filterParam.getDistrict());
		List<NewsCard> newsCards = postMapper.mapPostsToNewsCards(page.getContent());
		for (int i = 0; i < page.getContent().size(); i++) {
			newsCards.get(i).setMode(NewsModeConstant.NEWS_REJECT);
		}
		Page<NewsCard> paging = new Page<>(newsCards, page.getNumber(), page.getSize(), page.getTotalElements(),
				page.getTotalPages(), page.isLast(), page.isFirst());
		return paging;
	}

	@Override
	public Page<NewsCard> getDontPaymentOfUser(int pageNo, int pageSize, String field, 
			int mode, String username, FilterParam filterParam) {
		Pageable pageable = pageAndSortCommon.getPageable(pageNo, pageSize, field, mode);
		org.springframework.data.domain.Page<Post> page = postRepository.getDontPaymentOfUser(pageable, 
				username, filterParam.getTextSearch(), filterParam.getStartedDate(), 
				filterParam.getClosedDate(),
				filterParam.getTypeOfAcc() ,filterParam.getTypeOfNews(),
				filterParam.getProvince(), filterParam.getDistrict());
		List<NewsCard> newsCards = postMapper.mapPostsToNewsCards(page.getContent());
		for (int i = 0; i < page.getContent().size(); i++) {
			newsCards.get(i).setTotalAmount(page.getContent().get(i).getTotalAmount());
			newsCards.get(i).setMode(NewsModeConstant.NEWS_WAIT_PAYMENT);
		}
		Page<NewsCard> paging = new Page<>(newsCards, page.getNumber(), page.getSize(), page.getTotalElements(),
				page.getTotalPages(), page.isLast(), page.isFirst());
		return paging;
	}

	@Override
	public Page<NewsCard> getWaittingShowOfUser(int pageNo, int pageSize, String field,
			int mode, String username, FilterParam filterParam) {
		Pageable pageable = pageAndSortCommon.getPageable(pageNo, pageSize, field, mode);
		org.springframework.data.domain.Page<Post> page = postRepository.getWaittingShowOfUser(pageable, username,
				handleDateCommon.getCurrentDateTime(), filterParam.getTextSearch(), filterParam.getStartedDate(), 
				filterParam.getClosedDate(),
				filterParam.getTypeOfAcc() ,filterParam.getTypeOfNews(),
				filterParam.getProvince(), filterParam.getDistrict());
		List<NewsCard> newsCards = postMapper.mapPostsToNewsCards(page.getContent());
		for (int i = 0; i < page.getContent().size(); i++) {
			newsCards.get(i).setMode(NewsModeConstant.WAITING_SHOW);
		}
		Page<NewsCard> paging = new Page<>(newsCards, page.getNumber(), page.getSize(), page.getTotalElements(),
				page.getTotalPages(), page.isLast(), page.isFirst());
		return paging;
	}

	@Override
	public Page<NewsCard> getNewsShowingOfUser(int pageNo, int pageSize, String field, 
			int mode, String username, FilterParam filterParam) {
		Pageable pageable = pageAndSortCommon.getPageable(pageNo, pageSize, field, mode);
		org.springframework.data.domain.Page<Post> page = postRepository.getNewsShowingOfUser(pageable, 
				username,
				handleDateCommon.getCurrentDateTime(), 
				filterParam.getTextSearch(), filterParam.getStartedDate(), 
				filterParam.getClosedDate(),
				filterParam.getTypeOfAcc() ,filterParam.getTypeOfNews(),
				filterParam.getProvince(), filterParam.getDistrict());
		List<NewsCard> newsCards = postMapper.mapPostsToNewsCards(page.getContent());
		for (int i = 0; i < page.getContent().size(); i++) {
			newsCards.get(i).setMode(NewsModeConstant.SHOWING);
		}
		Page<NewsCard> paging = new Page<>(newsCards, page.getNumber(), page.getSize(), page.getTotalElements(),
				page.getTotalPages(), page.isLast(), page.isFirst());
		return paging;
	}

	@Override
	public Page<NewsCard> getNewsExpriedOfUser(int pageNo, int pageSize, 
			String field, int mode, String username, FilterParam filterParam) {
		Pageable pageable = pageAndSortCommon.getPageable(pageNo, pageSize, field, mode);
		org.springframework.data.domain.Page<Post> page = postRepository.getNewsExpriedOfUser(pageable, username,
				handleDateCommon.getCurrentDateTime(), filterParam.getTextSearch(), filterParam.getStartedDate(), 
				filterParam.getClosedDate(),
				filterParam.getTypeOfAcc() ,filterParam.getTypeOfNews(),
				filterParam.getProvince(), filterParam.getDistrict());
		List<NewsCard> newsCards = postMapper.mapPostsToNewsCards(page.getContent());
		for (int i = 0; i < page.getContent().size(); i++) {
			newsCards.get(i).setMode(NewsModeConstant.EXPRIED);
		}
		Page<NewsCard> paging = new Page<>(newsCards, page.getNumber(), page.getSize(), page.getTotalElements(),
				page.getTotalPages(), page.isLast(), page.isFirst());
		return paging;
	}

	@Override
	public Page<NewsCard> getNewsHiddenOfUser(int pageNo, int pageSize, 
			String field, int mode, String username, FilterParam filterParam) {
		Pageable pageable = pageAndSortCommon.getPageable(pageNo, pageSize, field, mode);
		org.springframework.data.domain.Page<Post> page = postRepository.getNewsHiddenOfUser(pageable, 
				username, filterParam.getTextSearch(), filterParam.getStartedDate(), 
				filterParam.getClosedDate(),
				filterParam.getTypeOfAcc() ,filterParam.getTypeOfNews(),
				filterParam.getProvince(), filterParam.getDistrict());
		List<NewsCard> newsCards = postMapper.mapPostsToNewsCards(page.getContent());
		for (int i = 0; i < page.getContent().size(); i++) {
			newsCards.get(i).setMode(NewsModeConstant.HINDDEN);
		}
		Page<NewsCard> paging = new Page<>(newsCards, page.getNumber(), page.getSize(), page.getTotalElements(),
				page.getTotalPages(), page.isLast(), page.isFirst());
		return paging;
	}

	@Override
	public Page<NewsCard> getNewsByTextSearch(int pageNo, int pageSize, String field, int mode, String username,
			String status, String textSearch, FilterParam filterForm) {
		Pageable pageable = pageAndSortCommon.getPageable(pageNo, pageSize, field, mode);
		org.springframework.data.domain.Page<Post> page = org.springframework.data.domain.Page.empty();
		List<NewsCard> newsCards = new ArrayList<>();
		switch (status) {
		case NewsModeConstant.WAITING_APROVED:
			page = postRepository.getNewsWaitApprovedByTextSearch(pageable, username, textSearch);
			newsCards = postMapper.mapPostsToNewsCards(page.getContent());
			for (int i = 0; i < page.getContent().size(); i++) {
				newsCards.get(i).setMode(NewsModeConstant.WAITING_APROVED);
			}
			break;
		case NewsModeConstant.NEWS_REJECT:
			page = postRepository.getNewsRejectByTextSearch(pageable, username, textSearch);
			newsCards = postMapper.mapPostsToNewsCards(page.getContent());
			for (int i = 0; i < page.getContent().size(); i++) {
				newsCards.get(i).setMode(NewsModeConstant.NEWS_REJECT);
			}
			break;
		case NewsModeConstant.NEWS_WAIT_PAYMENT:
			page = postRepository.getNewsWaitPaymentByTextSearch(pageable, username, textSearch);
			newsCards = postMapper.mapPostsToNewsCards(page.getContent());
			for (int i = 0; i < page.getContent().size(); i++) {
				newsCards.get(i).setTotalAmount(page.getContent().get(i).getTotalAmount());
				newsCards.get(i).setMode(NewsModeConstant.NEWS_WAIT_PAYMENT);
			}
			break;
		case NewsModeConstant.WAITING_SHOW:
			page = postRepository.getNewsWaitShowByTextSearch(pageable, username, textSearch,
					handleDateCommon.getCurrentDateTime());
			newsCards = postMapper.mapPostsToNewsCards(page.getContent());
			for (int i = 0; i < page.getContent().size(); i++) {
				newsCards.get(i).setMode(NewsModeConstant.WAITING_SHOW);
			}
			break;
		case NewsModeConstant.SHOWING:
			page = postRepository.getNewsShowByTextSearch(pageable, username, textSearch,
					handleDateCommon.getCurrentDateTime());
			newsCards = postMapper.mapPostsToNewsCards(page.getContent());
			for (int i = 0; i < page.getContent().size(); i++) {
				newsCards.get(i).setMode(NewsModeConstant.SHOWING);
			}
			break;
		case NewsModeConstant.HINDDEN:
			page = postRepository.getNewsHiddenByTextSearch(pageable, username, textSearch);
			newsCards = postMapper.mapPostsToNewsCards(page.getContent());
			for (int i = 0; i < page.getContent().size(); i++) {
				newsCards.get(i).setMode(NewsModeConstant.HINDDEN);
			}
			break;
		case NewsModeConstant.EXPRIED:
			page = postRepository.getNewsExpriedByTextSearch(pageable, username, textSearch,
					handleDateCommon.getCurrentDateTime());
			newsCards = postMapper.mapPostsToNewsCards(page.getContent());
			for (int i = 0; i < page.getContent().size(); i++) {
				newsCards.get(i).setMode(NewsModeConstant.EXPRIED);
			}
			break;
		default:
			page = postRepository.getNewsByTextSearch(pageable, username, textSearch);
			newsCards = postMapper.mapPostsToNewsCards(page.getContent());
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
						} else if (post.getClosedDate().compareTo(handleDateCommon.getCurrentDateTime()) < 0) {
							newsCards.get(i).setMode(NewsModeConstant.EXPRIED);
						}
					}
				} else if (post.getStatus() == 2) {
					newsCards.get(i).setMode(NewsModeConstant.NEWS_REJECT);
				} else if (post.getStatus() == 3) {
					newsCards.get(i).setMode(NewsModeConstant.HINDDEN);
				}
			}
			break;
		}
		Page<NewsCard> paging = new Page<>(newsCards, page.getNumber(), page.getSize(), page.getTotalElements(),
				page.getTotalPages(), page.isLast(), page.isFirst());
		return paging;
	}

	@Override
	public List<NewsCard> getNewsByStatus(String mode) {
		List<NewsCard> newsCards = new ArrayList<>();
		List<Post> lstPost = postRepository.findAll();
		newsCards = postMapper.mapPostsToNewsCards(lstPost);
		for (int i = 0; i < lstPost.size(); i++) {
			Post post = lstPost.get(i);
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
					} else if (post.getClosedDate().compareTo(handleDateCommon.getCurrentDateTime()) < 0) {
						newsCards.get(i).setMode(NewsModeConstant.EXPRIED);
					}
				}
			} else if (post.getStatus() == 2) {
				newsCards.get(i).setMode(NewsModeConstant.NEWS_REJECT);
			} else if (post.getStatus() == 3) {
				newsCards.get(i).setMode(NewsModeConstant.HINDDEN);
			}
		}
		switch (mode) {
		case NewsModeConstant.WAITING_APROVED:
			newsCards = newsCards.stream().filter((c) -> c.getMode() == NewsModeConstant.WAITING_APROVED)
					.collect(Collectors.toList());
			break;
		case NewsModeConstant.NEWS_REJECT:
			newsCards = newsCards.stream().filter((c) -> c.getMode() == NewsModeConstant.NEWS_REJECT)
					.collect(Collectors.toList());
			break;
		case NewsModeConstant.NEWS_WAIT_PAYMENT:
			newsCards = newsCards.stream().filter((c) -> c.getMode() == NewsModeConstant.NEWS_WAIT_PAYMENT)
					.collect(Collectors.toList());
			break;
		case NewsModeConstant.WAITING_SHOW:
			newsCards = newsCards.stream().filter((c) -> c.getMode() == NewsModeConstant.WAITING_SHOW)
					.collect(Collectors.toList());
			break;
		case NewsModeConstant.SHOWING:
			newsCards = newsCards.stream().filter((c) -> c.getMode() == NewsModeConstant.SHOWING)
					.collect(Collectors.toList());
			break;
		case NewsModeConstant.EXPRIED:
			newsCards = newsCards.stream().filter((c) -> c.getMode() == NewsModeConstant.EXPRIED)
					.collect(Collectors.toList());
			break;
		case NewsModeConstant.HINDDEN:
			newsCards = newsCards.stream().filter((c) -> c.getMode() == NewsModeConstant.HINDDEN)
					.collect(Collectors.toList());
			break;
		}
		return newsCards;
	}

	@Override
	public Boolean approvedNews(long id) {
		try {
			Post post = postRepository.getById(id);
			post.setStatus(1);
			post.setApprovedDate(handleDateCommon.getCurrentDateTime());
			postRepository.save(post);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean rejectedNews(long id) {
		try {
			Post post = postRepository.getById(id);
			post.setStatus(2);
			postRepository.save(post);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	@Transactional
	public Boolean insertReason(RejectDatasource rejectDatasource) {
		try {
			Post post = postRepository.getById(rejectDatasource.getId());
			post.setStatus(2);
			post.setReason(rejectDatasource.getReason());
			postRepository.save(post);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public String selReason(long id) {
		return postRepository.getById(id).getReason();
	}
	
	@Override
	@Transactional
	public void updateHiddenToPost(long id) {
		try {
			Post post = postRepository.getById(id);
			if(post.getStatus() == 1) {
				post.setStatus(3);
			}else {
				post.setStatus(1);
			}
			postRepository.save(post);
		} catch (Exception e) {
			throw new BadRequestException("Cập nhật không thành công, vui lòng thử lại");
		}
	}
	
	@Override
	@Transactional
	public void deletedPost(long postId) {
		try {
			Post post = postRepository.getById(postId);
			post.setStatus(4);
			postRepository.save(post);
		} catch (Exception e) {
			throw new BadRequestException("Xóa không thành công, vui lòng thử lại!!!");
		}
	}
}