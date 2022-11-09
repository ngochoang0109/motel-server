package com.server.kltn.motel.service.impl;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.server.kltn.motel.api.admin.payload.RejectDatasource;
import com.server.kltn.motel.api.user.payload.Cart;
import com.server.kltn.motel.api.user.payload.FilterParam;
import com.server.kltn.motel.api.user.payload.NewsCard;
import com.server.kltn.motel.api.user.payload.NewsCart;
import com.server.kltn.motel.common.HandleDateCommon;
import com.server.kltn.motel.common.PageAndSortCommon;
import com.server.kltn.motel.constant.NewsModeConstant;
import com.server.kltn.motel.entity.Payment;
import com.server.kltn.motel.entity.PaymentDetail;
import com.server.kltn.motel.entity.Post;
import com.server.kltn.motel.entity.User;
import com.server.kltn.motel.exception.ServerException;
import com.server.kltn.motel.mapper.DiscountMapper;
import com.server.kltn.motel.mapper.ExpenseMapper;
import com.server.kltn.motel.mapper.PostMapper;
import com.server.kltn.motel.page.Page;
import com.server.kltn.motel.repository.PaymentRepo;
import com.server.kltn.motel.repository.PostRepository;
import com.server.kltn.motel.repository.TypeOfAccRepository;
import com.server.kltn.motel.repository.UserRepository;
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

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PaymentRepo paymentRepo;

	@Autowired
	private ExpenseMapper expenseMapper;

	@Autowired
	private DiscountMapper discountMapper;
	
	@Autowired
	private TypeOfAccRepository typeOfAccRepository;

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
	public Page<NewsCard> getNewsWaitingAproved(int pageNo, int pageSize, String field, int mode, String username) {
		Pageable pageable = pageAndSortCommon.getPageable(pageNo, pageSize, field, mode);
		org.springframework.data.domain.Page<Post> page = postRepository.getNewsWaittingAprovedOfUser(pageable,
				username);
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
			newsCards.get(i).setTotalAmount(page.getContent().get(i).getTotalAmount());
			newsCards.get(i).setMode(NewsModeConstant.NEWS_WAIT_PAYMENT);
		}
		Page<NewsCard> paging = new Page<>(newsCards, page.getNumber(), page.getSize(), page.getTotalElements(),
				page.getTotalPages(), page.isLast(), page.isFirst());
		return paging;
	}

	@Override
	public Page<NewsCard> getWaittingShowOfUser(int pageNo, int pageSize, String field, int mode, String username) {
		Pageable pageable = pageAndSortCommon.getPageable(pageNo, pageSize, field, mode);
		org.springframework.data.domain.Page<Post> page = postRepository.getWaittingShowOfUser(pageable, username,
				handleDateCommon.getCurrentDateTime());
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
		org.springframework.data.domain.Page<Post> page = postRepository.getNewsShowingOfUser(pageable, username,
				handleDateCommon.getCurrentDateTime());
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
		org.springframework.data.domain.Page<Post> page = postRepository.getNewsExpriedOfUser(pageable, username,
				handleDateCommon.getCurrentDateTime());
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

	/*
	 * get cart of user from db with delflag= false check cart is exist check item
	 * of cart is not exist => add item of cart else => throw message
	 * "Item is exist" is not => create cart, add item to cart
	 */
	@Override
	@Transactional
	public Cart addNewsToCart(long idNews, String username) {
		Optional<User> user = userRepository.findByUsernameOrEmail(username, username);
		Optional<Post> postOptional = postRepository.findById(idNews);
		Post post = postOptional.get();
		Optional<Payment> paymentOptional = paymentRepo.getCartIsPayingByUser(username);

		// create temp cart
		// List<Payment> cart= new ArrayList<>();

		// add post to order detail
		List<PaymentDetail> paymentDetails = new ArrayList<>();
		PaymentDetail paymentDetail = new PaymentDetail();
		paymentDetail.setPost(post);
		paymentDetails.add(paymentDetail);

		if (!paymentOptional.isPresent()) {
			Payment cart = new Payment();
			cart.setUser(user.get());
			cart.setDelFlag(false);
			cart.setPaymentDetails(paymentDetails);
			paymentDetail.setPayment(cart);
			paymentRepo.save(cart);
		} else {
			if (paymentOptional.get().getPaymentDetails().stream()
					.filter(pd -> pd.getPost().equals(post)).findFirst()
					.isPresent()) {
				throw new ServerException("Bài viết đã có trong giỏ tin");
			}
			paymentDetail.setPayment(paymentOptional.get());
			paymentOptional.get().getPaymentDetails().add(paymentDetail);
			paymentRepo.save(paymentOptional.get());
		}
		return getCart(username);
	}

	@Override
	public Cart getCart(String username) {
		Optional<Payment> paymentOptional = paymentRepo.getCartIsPayingByUser(username);
		if (paymentOptional.isPresent()) {
			long totalPriceOfCart = 0;
			Payment payment = paymentOptional.get();
			Cart cart = new Cart();
			cart.setIdCart(payment.getId());

			List<NewsCart> newsCarts = new ArrayList<>();

			for (PaymentDetail paymentDetail : payment.getPaymentDetails()) {
				NewsCard newsCard= postMapper.mapPostToNewsCard(paymentDetail.getPost());
				NewsCart newsCart = new NewsCart(newsCard);
				totalPriceOfCart = totalPriceOfCart + paymentDetail.getPost().getTotalAmount();
				newsCart.setExpenseDatasource(
						expenseMapper.convertExpenseToExpenseDatasource(paymentDetail.getPost().getExpense()));
				newsCart.setDiscountDatasource(
						discountMapper.mapDiscountToDiscountDatasource(paymentDetail.getPost().getDiscount()));
				newsCart.setTypeOfAcc(paymentDetail.getPost().getAccomodation().getTypeOfAcc());
				newsCart.setNumDate(
					(int)ChronoUnit.DAYS.between( paymentDetail.getPost().getStartedDate(),paymentDetail.getPost().getClosedDate() ));
				newsCarts.add(newsCart);
			}
			cart.setTotalPriceOfCart(totalPriceOfCart);
			cart.setNewsCarts(newsCarts);
			return cart;
		}
		throw new ServerException("Chưa có giỏ hàng");
	}
}