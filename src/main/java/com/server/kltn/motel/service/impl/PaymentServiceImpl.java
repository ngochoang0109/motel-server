package com.server.kltn.motel.service.impl;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.server.kltn.motel.api.user.payload.HistoryPayload;
import com.server.kltn.motel.api.user.payload.PayOrder;
import com.server.kltn.motel.api.user.payload.PaymentPayload;
import com.server.kltn.motel.api.user.payload.paymentDetail.OrderDetailPayload;
import com.server.kltn.motel.common.HandleDateCommon;
import com.server.kltn.motel.config.VnpayConfig;
import com.server.kltn.motel.entity.Cart;
import com.server.kltn.motel.entity.CartDetail;
import com.server.kltn.motel.entity.Image;
import com.server.kltn.motel.entity.Payment;
import com.server.kltn.motel.entity.PaymentDetail;
import com.server.kltn.motel.entity.Post;
import com.server.kltn.motel.entity.User;
import com.server.kltn.motel.exception.BadRequestException;
import com.server.kltn.motel.exception.ResourceNotFoundException;
import com.server.kltn.motel.exception.ServerException;
import com.server.kltn.motel.repository.CartDetailRepository;
import com.server.kltn.motel.repository.CartRepository;
import com.server.kltn.motel.repository.PaymentRepository;
import com.server.kltn.motel.repository.PostRepository;
import com.server.kltn.motel.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private HandleDateCommon handleDateCommon;

	@Autowired
	private VnpayConfig vnpayConfig;

	private final int STR_LENGTH = 8;
	
	@Autowired
	private CartDetailRepository cartDetailRepository;
	
	@Autowired
	private PostRepository postRepository;

	@Override
	@Transactional
	public long createPayment(String username, long cartId) {
		Optional<Cart> cartOptional = cartRepository.getCartOfUser(username);
		if (cartOptional.isPresent()) {
			try {
				Cart cart = cartOptional.get();
				User user = cart.getUser();
				List<PaymentDetail> paymentDetails = new LinkedList<>();
				long totalAmount = 0;

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
						List<Long> postAmount = this.calPostAmount(cd.getPost());
						paymentDetail.setAmount(postAmount.get(0));
						totalAmount = totalAmount + postAmount.get(0);
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
		List<Long> resultList = new LinkedList<>();
		int numDate = (int) ChronoUnit.DAYS.between(post.getStartedDate(), post.getClosedDate());
		long amountPost = post.getExpense().getCost() * numDate;
		long amountDiscounted = 0;
		if (post.getDiscount() != null) {
			amountDiscounted = (amountPost * post.getDiscount().getPercent()) / 100;
			if (amountDiscounted > post.getDiscount().getPrice()) {
				amountDiscounted = post.getDiscount().getPrice();
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
		PaymentPayload paymentPayload = new PaymentPayload();
		List<OrderDetailPayload> orderDetailPayloads = new LinkedList<>();

		Payment payment = paymentRepository.getPaymentDetail(paymentId);
		paymentPayload.setId(payment.getId());
		paymentPayload.setTotalAmount(payment.getTotalAmount());

		for (PaymentDetail paymentDetail : payment.getPaymentDetails()) {
			OrderDetailPayload orderDetailPayload = new OrderDetailPayload();
			orderDetailPayload.setTitle(paymentDetail.getPost().getTitle());
			orderDetailPayload.setDiscounted(paymentDetail.getDiscount());
			int numDate = (int) ChronoUnit.DAYS.between(paymentDetail.getPost().getStartedDate(),
					paymentDetail.getPost().getClosedDate());
			orderDetailPayload.setOrginalAmount(paymentDetail.getPost().getExpense().getCost() * numDate);
			orderDetailPayload.setPostAmount(paymentDetail.getAmount());
			String srcImage = "";
			for (Image image : paymentDetail.getPost().getImages()) {
				if (image.getType().equals("avatar")) {
					srcImage = image.getSource();
					break;
				}
			}
			orderDetailPayload.setImage(srcImage);
			orderDetailPayloads.add(orderDetailPayload);
		}
		paymentPayload.setOrderDetailPayloads(orderDetailPayloads);
		return paymentPayload;
	}

	@Override
	public String payVnpay(PayOrder payOrder) {
		Payment payment = paymentRepository.getPaymentDetail(payOrder.getPaymentId());
		try {
			Map<String, String> vnp_Params = new HashMap<>();
			vnp_Params.put("vnp_Version", "2.1.0");
			vnp_Params.put("vnp_Command", "pay");
			vnp_Params.put("vnp_TmnCode", vnpayConfig.vnp_TmnCode);
			vnp_Params.put("vnp_Amount", Long.toString(payment.getTotalAmount() * 100));
			vnp_Params.put("vnp_CurrCode", "VND");
			vnp_Params.put("vnp_BankCode", payOrder.getBankCode());
			vnp_Params.put("vnp_TxnRef", vnpayConfig.getRandomNumber(STR_LENGTH));
			vnp_Params.put("vnp_OrderInfo", Long.toString(payment.getId()));
			vnp_Params.put("vnp_OrderType", "other");
			vnp_Params.put("vnp_Locale", "vn");
			vnp_Params.put("vnp_ReturnUrl", "http://localhost:3000/trang-chu/quan-ly-bai-viet/lich-su-giao-dich");
			vnp_Params.put("vnp_IpAddr", payOrder.getIpUser());

			Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			String vnp_CreateDate = formatter.format(cld.getTime());
			vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
			cld.add(Calendar.MINUTE, 15);

			String vnp_ExpireDate = formatter.format(cld.getTime());
			// Add Params of 2.0.1 Version
			vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

			vnp_Params.put("vnp_Bill_FirstName", payment.getUser().getFullname());
			vnp_Params.put("vnp_Inv_Email", payment.getUser().getUsername());
			vnp_Params.put("vnp_Bill_Address", payment.getUser().getAddress());
			vnp_Params.put("vnp_Inv_Phone", payment.getUser().getPhone());

			List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
			Collections.sort(fieldNames);
			StringBuilder hashData = new StringBuilder();
			StringBuilder query = new StringBuilder();
			Iterator<String> itr = fieldNames.iterator();
			while (itr.hasNext()) {
				String fieldName = (String) itr.next();
				String fieldValue = (String) vnp_Params.get(fieldName);
				if ((fieldValue != null) && (fieldValue.length() > 0)) {
					// Build hash data
					hashData.append(fieldName);
					hashData.append('=');
					hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
					// Build query
					query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
					query.append('=');
					query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
					if (itr.hasNext()) {
						query.append('&');
						hashData.append('&');
					}
				}
			}
			String queryUrl = query.toString();
			String vnp_SecureHash = vnpayConfig.hmacSHA512(vnpayConfig.vnp_HashSecret, hashData.toString());
			queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
			return queryUrl;
		} catch (Exception ex) {
			throw new ResourceNotFoundException("Lỗi gửi yêu cầu", "paymentId", payment.getId());
		}
	}
	
	@Override
	@Transactional
	public void checkUpdatePayment(long paymentId, String payDate, String responseCode) {
		Payment payment = paymentRepository.getPaymentDetail(paymentId);
		User user = payment.getUser();
		Cart cart = user.getCart();
		if ("00".equals(responseCode)) {
			DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
			LocalDateTime formatDateTime = LocalDateTime.parse(payDate, FORMATTER);
			payment.setCreatedDate(formatDateTime);
			payment.setPayment(true);
			for (PaymentDetail pd : payment.getPaymentDetails()) {
				postRepository.updateIsPayment(pd.getPost().getId());
				CartDetail cartDetail = cart.getCartDetails().stream()
				  .filter(item -> item.getPost().getId()==(pd.getPost().getId())).findFirst().get();
				cartDetailRepository.deleteCartDetail(cartDetail.getCart().getId(), cartDetail.getPost().getId());
			}
			payment.setStatus(1);
			paymentRepository.save(payment);
		}else {
			payment.setStatus(2);
			paymentRepository.save(payment);
			throw new BadRequestException("Giao dịch thất bại, vui lòng thanh toán lại");
		}
	}
	
	@Override
	public List<HistoryPayload> getHistoryPayment(String username) {
		List<HistoryPayload> historyPayloads= new ArrayList<>();
		List<Payment> payments = paymentRepository.getPaymentHistory(username);
		for (Payment payment : payments) {
			HistoryPayload hp = new HistoryPayload();
			hp.setPayDate(payment.getCreatedDate().toString());
			hp.setPaymentId(payment.getId());
			hp.setPrice(payment.getTotalAmount());
			hp.setStatus(payment.getStatus());
			historyPayloads.add(hp);
		}
		return historyPayloads;
	}
}
