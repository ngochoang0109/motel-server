package com.server.kltn.motel.api.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.kltn.motel.api.user.payload.CartPayload;
import com.server.kltn.motel.service.CartService;

@RestController
@RequestMapping("/api")
public class CartController {

	@Autowired
	private CartService cartService;
	
	@PostMapping(value = "/cart-management/deleted-item/{cartId}/{newsId}")
	@PreAuthorize("hasRole('ROLE_USER')"+"||" + "hasRole('ROLE_ADMIN')")
	public void getCartOfUser(Authentication authentication,@PathVariable("cartId") long cartId, 
			@PathVariable("newsId") long newsId) {
		cartService.deletedCart(authentication.getName(),cartId, newsId);
	}
	
	@GetMapping(value = "/news-management/get-cart")
	@PreAuthorize("hasRole('ROLE_USER')"+"||" + "hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> getCartOfUser(Authentication authentication) {
		CartPayload cart= cartService.getCart(authentication.getName());
		return new ResponseEntity<CartPayload>(cart, HttpStatus.OK);
	}
	
	@PostMapping(value = "/news-management/add-item-to-cart")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> getNewsByTextSearch(@RequestParam(value = "idNews") int idNews,
			Authentication authentication) {
		CartPayload cart= cartService.addNewsToCart(idNews, authentication.getName());
		return new ResponseEntity<CartPayload>(cart, HttpStatus.OK);
	}
}
