package com.server.kltn.motel.api.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.kltn.motel.api.user.payload.CartPayload;
import com.server.kltn.motel.service.CartService;

@RestController
@RequestMapping("/api")
public class CartController {
	
//	@Autowired
//	private CartService cartService;
	
//	@PostMapping(value = "/cart-management/deleted-item/{idCart}/{id}")
//	@PreAuthorize("hasRole('ROLE_USER')"+"||" + "hasRole('ROLE_ADMIN')")
//	public void getCartOfUser(Authentication authentication, 
//			@PathVariable("idCart") long idCart, 
//			@PathVariable("id") long id) {
//		cartService.deletedCart(authentication.getName(), idCart, id);
//	}
	
}
