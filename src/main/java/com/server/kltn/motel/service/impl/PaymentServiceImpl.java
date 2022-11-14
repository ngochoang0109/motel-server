package com.server.kltn.motel.service.impl;

import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.server.kltn.motel.api.user.payload.OrderDetailPayload;
import com.server.kltn.motel.api.user.payload.PaymentPayload;
import com.server.kltn.motel.common.HandleDateCommon;
import com.server.kltn.motel.entity.Cart;
import com.server.kltn.motel.entity.CartDetail;
import com.server.kltn.motel.entity.Image;
import com.server.kltn.motel.entity.Payment;
import com.server.kltn.motel.entity.PaymentDetail;
import com.server.kltn.motel.entity.Post;
import com.server.kltn.motel.entity.User;
import com.server.kltn.motel.exception.ResourceNotFoundException;
import com.server.kltn.motel.exception.ServerException;
import com.server.kltn.motel.repository.CartRepository;
import com.server.kltn.motel.repository.PaymentRepository;
import com.server.kltn.motel.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private HandleDateCommon handleDateCommon;

	@Override
	@Transactional
	public long createPayment(String username, long cartId) {
		Optional<Cart> cartOptional = cartRepository.getCartOfUser(username);
		if (cartOptional.isPresent()) {
			try {
				Cart cart = cartOptional.get();
				User user = cart.getUser();
				List<PaymentDetail> paymentDetails = new LinkedList<>();
				long totalAmount=0;
				
				Payment payment = new Payment();
				payment.setUser(user);
				payment.setCreatedDate(handleDateCommon.getCurrentDateTime());
				payment = paymentRepository.save(payment);

				for (CartDetail cd : cart.getCartDetails()) {
					if (cd.isChecked()) {
						PaymentDetail paymentDetail = new PaymentDetail();
						paymentDetail.setPayment(payment);
						paymentDetail.setPost(cd.getPost());
						paymentDetails.add(paymentDetail);
						List<Long> postAmount= this.calPostAmount(cd.getPost());
						paymentDetail.setAmount(postAmount.get(0));
						totalAmount=totalAmount+postAmount.get(0);
						paymentDetail.setDiscount(postAmount.get(1));
					}
				}
				payment.setPaymentDetails(paymentDetails);
				payment.setTotalAmount(totalAmount);
				payment = paymentRepository.save(payment);
				return payment.getId();
			} catch (Exception e) {
				throw new ServerException("Tạo đơn thanh toán không thành công");
			}
		} else {
			throw new ResourceNotFoundException("Giỏ tin không tồn tại", "cart", cartId);
		}
	}

	private List<Long> calPostAmount(Post post) {
		List<Long> resultList= new LinkedList<>();
		int numDate= (int) ChronoUnit.DAYS.between(post.getStartedDate(),post.getClosedDate());
		long amountPost = post.getExpense().getCost() * numDate;
		long amountDiscounted= 0;
		if (post.getDiscount() != null) {
			amountDiscounted = (amountPost * post.getDiscount().getPercent()) / 100;
			if (amountDiscounted > post.getDiscount().getPrice()) {
				amountDiscounted=post.getDiscount().getPrice();
				amountPost = amountPost - post.getDiscount().getPrice();
			} else {
				amountPost = amountPost - amountDiscounted;
			}
		}
		resultList.add(amountPost);
		resultList.add(amountDiscounted);
		return resultList;
	}
	
	@Override
	public PaymentPayload getPaymentDetail(long paymentId) {
		PaymentPayload paymentPayload= new PaymentPayload();
		List<OrderDetailPayload> orderDetailPayloads= new LinkedList<>();
		
		Payment payment= paymentRepository.getPaymentDetail(paymentId);
		paymentPayload.setId(payment.getId());
		paymentPayload.setTotalAmount(payment.getTotalAmount());
		
		for (PaymentDetail paymentDetail : payment.getPaymentDetails()) {
			OrderDetailPayload orderDetailPayload= new OrderDetailPayload();
			orderDetailPayload.setTitle(paymentDetail.getPost().getTitle());
			orderDetailPayload.setDiscounted(paymentDetail.getDiscount());
			int numDate= (int) ChronoUnit.DAYS.between(paymentDetail.getPost().getStartedDate(),paymentDetail.getPost().getClosedDate());
			orderDetailPayload.setOrginalAmount(paymentDetail.getPost().getExpense().getCost()* numDate);
			orderDetailPayload.setPostAmount(paymentDetail.getAmount());
			String srcImage="";
			for (Image image : paymentDetail.getPost().getImages()) {
				if (image.getType().equals("avatar")) {
					srcImage= image.getSource();
					break;
				}
			}
			orderDetailPayload.setImage(srcImage);
			orderDetailPayloads.add(orderDetailPayload);
		}
		paymentPayload.setOrderDetailPayloads(orderDetailPayloads);
		return paymentPayload;
	}
}
