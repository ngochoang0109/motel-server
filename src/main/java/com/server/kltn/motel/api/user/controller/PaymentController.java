package com.server.kltn.motel.api.user.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.server.kltn.motel.api.user.payload.HistoryPayload;
import com.server.kltn.motel.api.user.payload.PayOrder;
import com.server.kltn.motel.api.user.payload.PaymentPayload;
import com.server.kltn.motel.service.PaymentService;

@RestController
@RequestMapping("/api")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@PostMapping(value = "/payment/create-payment/{cartId}")
	@PreAuthorize("hasRole('ROLE_USER')"+"||" + "hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> createPayment(Authentication authentication, @PathVariable("cartId") long cartId) {
		long id=paymentService.createPayment(authentication.getName(), cartId);
		return new ResponseEntity<>(id,HttpStatus.OK);
	}
	
	@GetMapping(value = "/payment/get-payment/{paymentId}")
	@PreAuthorize("hasRole('ROLE_USER')"+"||" + "hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> getPayment(Authentication authentication, @PathVariable("paymentId") long paymentId) {
		return new ResponseEntity<PaymentPayload>(paymentService.getPaymentDetail(paymentId),HttpStatus.OK);
	}
	
	@PostMapping(value = "/payment/pay-vnpay")
	@PreAuthorize("hasRole('ROLE_USER')"+"||" + "hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> payVnpay(Authentication authentication, @RequestBody PayOrder payOrder){
		String urlString= paymentService.payVnpay(payOrder);
		return new ResponseEntity<String>(urlString,HttpStatus.OK);
	}
	
	@GetMapping(value = "/payment/history")
	@PreAuthorize("hasRole('ROLE_USER')"+"||" + "hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> getHistoryPayment(Authentication authentication){
		return new ResponseEntity<List<HistoryPayload>>(paymentService.getHistoryPayment(authentication.getName()),HttpStatus.OK);
	}
	
	@PostMapping(value = "/payment/update-payment-vnpay")
	@PreAuthorize("hasRole('ROLE_USER')"+"||" + "hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> checkPaymentVnpay(Authentication authentication, 
			@RequestParam("paymentId") long paymentId, @RequestParam("payDate") String payDate,
			@RequestParam("responseCode") String responseCode){
		paymentService.checkUpdatePayment(paymentId, payDate, responseCode);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}
