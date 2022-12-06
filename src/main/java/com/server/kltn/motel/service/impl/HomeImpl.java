package com.server.kltn.motel.service.impl;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.server.kltn.motel.api.user.payload.NewsCard;
import com.server.kltn.motel.api.user.payload.HomePayload.CountNews;
import com.server.kltn.motel.api.user.payload.HomePayload.SearchParam;
import com.server.kltn.motel.common.HandleDateCommon;
import com.server.kltn.motel.entity.Expense;
import com.server.kltn.motel.entity.Post;
import com.server.kltn.motel.mapper.PostMapper;
import com.server.kltn.motel.page.Page;
import com.server.kltn.motel.repository.AccomodationRepo;
import com.server.kltn.motel.repository.ExpenseRepository;
import com.server.kltn.motel.repository.PostRepository;
import com.server.kltn.motel.sepecification.SearchSepecification;
import com.server.kltn.motel.service.HomeService;

@Service
public class HomeImpl implements HomeService {

	@Autowired
	private PostRepository postRepository;
	@Autowired
	private PostMapper postMapper;
	@Autowired
	private HandleDateCommon handleDateCommon;

	@Autowired
	private SearchSepecification searchSepecification;

	@Autowired
	private ExpenseRepository expenseRepository;

	@Autowired
	private AccomodationRepo accomodationRepo;

	private List<Post> sortPriorityAndDate(List<Post> posts, String fieldSort, int mode) {
		/* mode: 1 ascend, 2 descend */
		Comparator<Post> priority = (post1, post2) -> {
			if (fieldSort.equals("startedDate")) {
				if (mode == 2) {
					return post2.getCreatedDate().compareTo(post1.getCreatedDate());
				}
			} else if (fieldSort.equals("price")) {
				if (mode == 1) {
					return Long.compare(post1.getAccomodation().getPrice(), post2.getAccomodation().getPrice());
				} else {
					return Long.compare(post2.getAccomodation().getPrice(), post1.getAccomodation().getPrice());
				}
			} else if (fieldSort.equals("area")) {
				if (mode == 1) {
					return Long.compare(post1.getAccomodation().getArea(), post2.getAccomodation().getArea());
				} else {
					return Long.compare(post2.getAccomodation().getArea(), post1.getAccomodation().getArea());
				}
			}
			return Long.compare(post2.getExpense().getCost(), post1.getExpense().getCost());
		};
		List<Post> postSorted = posts.stream().sorted(priority).collect(Collectors.toList());
		return postSorted;
	}

	@Override
	public Page<NewsCard> searchPageCard(int pageNo, int pageSize, String field, int mode, SearchParam searchParam) {
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("expense.cost").descending());
		searchSepecification.searchNewsByParamQuery(searchParam, handleDateCommon.getCurrentDateTime());
		org.springframework.data.domain.Page<Post> posts = postRepository.findAll(
				searchSepecification.searchNewsByParamQuery(searchParam, handleDateCommon.getCurrentDateTime()),
				pageable);

		List<Post> postSorted = sortPriorityAndDate(posts.getContent(), field, mode);
		if (field.equals("startedDate") && mode ==1 ) {
			postSorted = this.sortBatchExpenseByDateTime(postSorted);
		}
		Page<NewsCard> paging = new Page<>(postMapper.mapPostsToNewsCards(postSorted), posts.getNumber(),
				posts.getSize(), posts.getTotalElements(), posts.getTotalPages(), posts.isLast(), posts.isFirst());
		return paging;
	}
	
	private List<Post> sortBatchExpenseByDateTime(List<Post> postSorted) {
		List<Expense> expenses= expenseRepository.findAll();
		List<Post> afterSortList = new ArrayList<>();
		for (Expense expense : expenses) {
			List<Post> posts = new ArrayList<>();
			for (Post p : postSorted) {
				if (p.getExpense().getId() == (expense.getId())) {
					posts.add(p);
				}
			}
			afterSortList.addAll(0,posts.stream().sorted((p1, p2)-> p2.getStartedDate().compareTo(p1.getStartedDate())).collect(Collectors.toList()));
		}
		return afterSortList;
	}

	@Override
	public List<CountNews> getCountByProvince() {
		List<CountNews> getCount = accomodationRepo.countByProvince(handleDateCommon.getCurrentDateTime());
		return getCount;
	}
}
