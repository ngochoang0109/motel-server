package com.server.kltn.motel.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.kltn.motel.api.authentication.payload.NewUserDataset;
import com.server.kltn.motel.common.HandleDateCommon;
import com.server.kltn.motel.common.MessageCommon;
import com.server.kltn.motel.entity.Role;
import com.server.kltn.motel.entity.User;
import com.server.kltn.motel.exception.BadRequestException;
import com.server.kltn.motel.repository.UserRepository;
import com.server.kltn.motel.service.AuthenService;

@Service
public class AuthenServiceImpl implements AuthenService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	@Transactional
	public MessageCommon registerUser(NewUserDataset newUserDataset) {
		if (userRepository.existsByEmail(newUserDataset.getEmail())) {
			throw new BadRequestException("Email đã được sử dụng!");
		}

		if (userRepository.existsByUsername(newUserDataset.getUsername())) {
			throw new BadRequestException("Username đã tồn tại!");
		}

		
		// Creating user's account
		User user = new User();

		user.setEmail(newUserDataset.getEmail());
		user.setUsername(newUserDataset.getUsername());
		user.setFullname(newUserDataset.getFullName());
		user.setPhone(newUserDataset.getPhone());
		user.setEnable(true);
		user.setPassword(passwordEncoder.encode(newUserDataset.getPassword()));
		user.setCreateDate(new HandleDateCommon().getCurrentDateTime());
		List<Role> roles= new ArrayList<>();
		roles.add(new Role(1,"ROLE_USER"));
		user.setRoles(roles);
		try {
			userRepository.save(user);
			return new MessageCommon(true,"Đăng ký tài khoản thành công");
		} catch (Exception e) {
			return new MessageCommon(false, "Đăng ký thất bại, hãy đăng ký lại");
		}
	}
}
