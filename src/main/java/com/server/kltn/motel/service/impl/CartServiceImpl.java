package com.server.kltn.motel.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.kltn.motel.api.user.payload.CartPayload;
import com.server.kltn.motel.api.user.payload.NewsCard;
import com.server.kltn.motel.api.user.payload.NewsCart;
import com.server.kltn.motel.api.user.payload.UpdateItemCart;
import com.server.kltn.motel.common.HandleDateCommon;
import com.server.kltn.motel.entity.Cart;
import com.server.kltn.motel.entity.CartDetail;
import com.server.kltn.motel.entity.Post;
import com.server.kltn.motel.exception.ResourceNotFoundException;
import com.server.kltn.motel.exception.ServerException;
import com.server.kltn.motel.mapper.DiscountMapper;
import com.server.kltn.motel.mapper.ExpenseMapper;
import com.server.kltn.motel.mapper.PostMapper;
import com.server.kltn.motel.repository.CartDetailRepository;
import com.server.kltn.motel.repository.CartRepository;
import com.server.kltn.motel.repository.PostRepository;
import com.server.kltn.motel.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private PostMapper postMapper;

	@Autowired
	private ExpenseMapper expenseMapper;

	@Autowired
	private DiscountMapper discountMapper;

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private CartDetailRepository cartDetailRepository;
	
	@Autowired
	private HandleDateCommon handleDateCommon;
	
	@Override
	@Transactional
	public void deletedCart(String username, long cartId, long newsId) {
		CartDetail cartDetail= cartDetailRepository.getPostOfCart(cartId, newsId);
		this.cartDetailRepository.delete(cartDetail);
	}

	@Override
	public CartPayload getCart(String username) {
		Optional<Cart> cartOptional = cartRepository.getCartOfUser(username);
		if (cartOptional.isPresent()) {
			long totalPriceOfCart = 0;
			Cart cart = cartOptional.get();
			CartPayload cartPayload = new CartPayload();
			cartPayload.setIdCart(cart.getId());

			List<NewsCart> newsCarts = new ArrayList<>();

			for (CartDetail paymentDetail : cart.getCartDetails()) {
				NewsCard newsCard = postMapper.mapPostToNewsCard(paymentDetail.getPost());
				NewsCart newsCart = new NewsCart(newsCard);
				if (paymentDetail.isChecked()) {
					totalPriceOfCart = totalPriceOfCart + paymentDetail.getPost().getTotalAmount();
				}
				newsCart.setExpenseDatasource(
						expenseMapper.convertExpenseToExpenseDatasource(paymentDetail.getPost().getExpense()));
				if (paymentDetail.getPost().getDiscount() != null) {
					newsCart.setDiscountDatasource(
							discountMapper.mapDiscountToDiscountDatasource(paymentDetail.getPost().getDiscount()));
				}
				newsCart.setTypeOfAcc(paymentDetail.getPost().getAccomodation().getTypeOfAcc());
				newsCart.setNumDate((int) ChronoUnit.DAYS.between(paymentDetail.getPost().getStartedDate(),
						paymentDetail.getPost().getClosedDate()));
				newsCart.setChecked(paymentDetail.isChecked());
				newsCarts.add(newsCart);
			}
			cartPayload.setTotalPriceOfCart(totalPriceOfCart);
			cartPayload.setNewsCarts(newsCarts);
			return cartPayload;
		}
		throw new ResourceNotFoundException("Chưa có giỏ hàng", "cartOptional", "");
	}

	@Override
	@Transactional
	public CartPayload addNewsToCart(long idNews, String username) {
		Optional<Post> postOptional = postRepository.findById(idNews);
		Post post = postOptional.get();
		Optional<Cart> cartOptional = cartRepository.getCartOfUser(username);
		
		if (cartOptional.get().getCartDetails().stream().filter(pd -> pd.getPost().equals(post)).findFirst()
				.isPresent()) {
			throw new ServerException("Bài viết đã có trong giỏ tin");
		}

		// add post to cart detail
		CartDetail cartDetail = new CartDetail();
		cartDetail.setPost(post);
		cartDetail.setChecked(true);
		cartDetail.setCart(cartOptional.get());
		cartOptional.get().getCartDetails().add(cartDetail);
		cartRepository.save(cartOptional.get());
		return getCart(username);
	}
	
	@Override
	@Transactional
	public CartPayload updateItem(String username,UpdateItemCart updateItemCart, long cartId, long newsId) {
		CartDetail cartDetail= cartDetailRepository.getPostOfCart(cartId, newsId);
		Post post= cartDetail.getPost();
	
		post.setStartedDate(handleDateCommon.convertStringDateToLocalDateTime(updateItemCart.getStartedDate())); 
		post.setClosedDate(handleDateCommon.convertStringDateToLocalDateTime(updateItemCart.getStartedDate())
				.plusDays(updateItemCart.getNumDate()));
		long amountPost=post.getExpense().getCost()* updateItemCart.getNumDate();
		if (post.getDiscount()!=null) {
			long amountDiscounted= (amountPost* post.getDiscount().getPercent())/100;
			if (amountDiscounted> post.getDiscount().getPrice()) {
				amountPost= amountPost-post.getDiscount().getPrice();
			}else {
				amountPost= amountPost- amountDiscounted;
			}
		}
		post.setTotalAmount(amountPost);
		cartDetail.setChecked(updateItemCart.isChecked());
		cartDetail.setChecked(updateItemCart.isChecked());
		cartDetail.setPost(post);
		cartDetailRepository.save(cartDetail);
		return this.getCart(username);
	}
	
	@Override
	@Transactional
	public CartPayload updateItem(String username, List<UpdateItemCart> updateItemCart, long cartId) {
		for (UpdateItemCart updateItem : updateItemCart) {
			this.updateItem(username, updateItem, cartId, updateItem.getId());
		}
		return this.getCart(username);
	}
}
