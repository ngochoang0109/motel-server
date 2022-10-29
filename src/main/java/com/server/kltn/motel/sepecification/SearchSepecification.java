package com.server.kltn.motel.sepecification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import com.server.kltn.motel.api.user.payload.SearchParam;
import com.server.kltn.motel.entity.Post;

@Component
public class SearchSepecification {
	public Specification<Post> searchNewsByParamQuery(SearchParam searchParam, LocalDateTime currenTime) {
		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("startedDate"), currenTime));
			predicates.add(criteriaBuilder.greaterThan(root.get("closedDate"), currenTime));
			predicates.add(criteriaBuilder.equal(root.get("status"), 1));
			predicates.add(criteriaBuilder.equal(root.get("isPayment"), true));
			if (searchParam.getType() != 0) {
				predicates.add(criteriaBuilder.equal(root.get("accomodation").get("typeOfAcc").get("id"),
						searchParam.getType()));
			}
			if (!searchParam.getProvince().isEmpty()) {
				predicates
						.add(criteriaBuilder.like(root.get("accomodation").get("province"), searchParam.getProvince()));
			}
			if (!searchParam.getDistrict().isEmpty()) {
				predicates.add(
						criteriaBuilder.like(root.get("accomodation").get("dicstrict"), searchParam.getDistrict()));
			}
			if (!searchParam.getWard().isEmpty()) {
				predicates.add(criteriaBuilder.like(root.get("accomodation").get("ward"), searchParam.getWard()));
			}
			if (searchParam.getPriceFrom() < searchParam.getPriceTo() || searchParam.getPriceTo() != 0) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("accomodation").get("price"),
						searchParam.getPriceFrom()));
				if (searchParam.getPriceTo() < 100000000) {
					predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("accomodation").get("price"),
							searchParam.getPriceTo()));
				}
			}
			if (searchParam.getAreaFrom() < searchParam.getAreaTo() || searchParam.getAreaTo() != 0) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("accomodation").get("area"),
						searchParam.getAreaFrom()));
				if (searchParam.getAreaTo() < 1000) {
					predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("accomodation").get("area"),
							searchParam.getAreaTo()));
				}
			}
			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
	}
}
