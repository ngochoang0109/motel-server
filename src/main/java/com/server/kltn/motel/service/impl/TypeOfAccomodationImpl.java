package com.server.kltn.motel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.kltn.motel.entity.TypeOfAcc;
import com.server.kltn.motel.repository.TypeOfAccRepository;
import com.server.kltn.motel.service.TypeOffAccService;

@Service
public class TypeOfAccomodationImpl implements TypeOffAccService{
	
	@Autowired
	private TypeOfAccRepository typeOfAccRepository;
	
	@Override
	public List<TypeOfAcc> getListTypeOfAcc() {
		return typeOfAccRepository.getAll();
	}
	
}