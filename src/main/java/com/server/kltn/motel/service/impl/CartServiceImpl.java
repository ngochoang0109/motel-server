package com.server.kltn.motel.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import com.server.kltn.motel.entity.Cart;
import com.server.kltn.motel.entity.CartDetail;
import com.server.kltn.motel.exception.ResourceNotFoundException;
import com.server.kltn.motel.repository.CartDetailRepository;
//import com.server.kltn.motel.repository.PaymentRepo;
import com.server.kltn.motel.service.CartService;

public class CartServiceImpl implements CartService{
//	
//	@Autowired
//	private PaymentRepo paymentRepo; 
//	
//	@Autowired
//	private CartDetailRepository cartDetailRepository;
//	
//	@Override
//	@Transactional
//	public void deletedCart(String username, long idCart, long id) {
//		Cart cart= paymentRepo
//				.getCartById(idCart).orElseThrow(() -> new ResourceNotFoundException("cart", "cartId", idCart));
//		DetailCart cartItem= cart.getPaymentDetails().stream().filter(item-> item.getPost().getId() == id).findFirst().get();
//		cartDetailRepository.delete(cartItem);
//	}
//	
}
