package com.server.kltn.motel.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PageAndSortCommon {
	/*mode: 1 ascend, 2 descend*/
	public Pageable getPageable(int pageNo, int pageSize, String field, int mode) {
		Pageable paging=PageRequest.of(pageNo, pageSize,mode==2?Sort.by("accomodation.price").descending():Sort.by("accomodation.price").ascending());
		return paging;
	}
}
