package com.server.kltn.motel.sepecification;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import com.server.kltn.motel.api.user.payload.HomePayload.SearchParam;
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
			List<Predicate> formSearchPredicates = new ArrayList<>();
			
			List<Predicate> searchByTextSearch = new LinkedList<>();
			if (!searchParam.getTextSearch().isEmpty()) {
				searchByTextSearch.add(
						criteriaBuilder.like(root.get("title").as(String.class),String.format("%%%s%%",searchParam.getTextSearch())));
				searchByTextSearch.add(
						criteriaBuilder.like(root.get("accomodation").get("dicstrict").as(String.class),String.format("%%%s%%",searchParam.getTextSearch())));
				searchByTextSearch.add(
						criteriaBuilder.like(root.get("accomodation").get("street").as(String.class),String.format("%%%s%%",searchParam.getTextSearch())));
				searchByTextSearch.add(
						criteriaBuilder.like(root.get("accomodation").get("province").as(String.class),String.format("%%%s%%",searchParam.getTextSearch())));
				searchByTextSearch.add(
						criteriaBuilder.like(root.get("accomodation").get("ward").as(String.class),String.format("%%%s%%",searchParam.getTextSearch())));
				searchByTextSearch.add(
						criteriaBuilder.like(root.get("accomodation").get("tower").as(String.class),String.format("%%%s%%",searchParam.getTextSearch())));
			}
			if (searchByTextSearch.size() > 0) {
				predicates.add(criteriaBuilder.or(searchByTextSearch.toArray(new Predicate[searchByTextSearch.size()])));
			}
			
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
				if (searchParam.getAreaTo() < 150) {
					predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("accomodation").get("area"),
							searchParam.getAreaTo()));
				}
			}
			
			// filter number of beds
			List<Predicate> searchBeds = new LinkedList<>();
			if (searchParam.getNumBeds().size()!=0) {
				searchBeds.add(root.get("accomodation").get("numOfBed").in(searchParam.getNumBeds()));
				if(!searchParam.getNumBeds().stream().filter(el -> el == 5).findFirst().isEmpty()) {
					searchBeds.add(criteriaBuilder.greaterThan(root.get("accomodation").get("numOfBed"), 5));
				}
			}
			if (searchBeds.size() > 0) {
				formSearchPredicates.add(criteriaBuilder.or(searchBeds.toArray(new Predicate[searchBeds.size()])));
			}
			
			
			// filter direction house
			List<Predicate> searchDirectionHouse = new LinkedList<>();
			if (searchParam.getDirectionHouse().size()!=0) {
				for (String dh : searchParam.getDirectionHouse()) {
					searchDirectionHouse.add(criteriaBuilder.equal(root.get("accomodation").get("directionHouse"),  dh));
				}
			}
			if (searchDirectionHouse.size() > 0) {
				formSearchPredicates.add(criteriaBuilder.or(searchDirectionHouse.toArray(new Predicate[searchDirectionHouse.size()])));
			}
			
			// filter media
			List<Predicate> searchMedia = new LinkedList<>();
			if (searchParam.getMedia().size()!=0) {
				for (int m : searchParam.getMedia()) {
					if(m == 1) {
						searchMedia.add(criteriaBuilder.greaterThan(criteriaBuilder.size(root.get("images")),0));
					}else if(m == 2) {
						searchMedia.add(criteriaBuilder.greaterThan(criteriaBuilder.size(root.get("videos")),0));
					}
				}
			}
			if (searchMedia.size() > 0) {
				formSearchPredicates.add(criteriaBuilder.or(searchMedia.toArray(new Predicate[searchMedia.size()])));
			}
			
			Predicate finalCombinePredicate;
			Predicate combinePredicate = criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
				
			if (formSearchPredicates.size() > 0) {
				Predicate combineFormSearchPredicate = criteriaBuilder.and(formSearchPredicates.toArray(new Predicate[formSearchPredicates.size()]));
				finalCombinePredicate = criteriaBuilder.and(combinePredicate, combineFormSearchPredicate);
			} else {
				finalCombinePredicate = criteriaBuilder.or(combinePredicate);
			}
			return finalCombinePredicate;
		};
	}
}
