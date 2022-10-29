package com.server.kltn.motel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.kltn.motel.api.authentication.payload.UserInfor;
import com.server.kltn.motel.entity.User;
import com.server.kltn.motel.exception.ResourceNotFoundException;
import com.server.kltn.motel.repository.UserRepository;
import com.server.kltn.motel.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;
	@Override
	public User findUserByUsernameOrEmail(String username, String email) {
		User user= userRepository.findByUsernameOrEmail(username,email)
				.orElseThrow(() -> new ResourceNotFoundException("user", "username", username));
		return user;
	}
	
	@Override
	public UserInfor getCurrentUser(String username) {
		User user= userRepository.findByUsernameOrEmail(username,username)
				.orElseThrow(() -> new ResourceNotFoundException("user", "username", username));
		UserInfor userInfor= new UserInfor();
		userInfor.setId(user.getId());
		userInfor.setFullname(user.getFullname());
		userInfor.setAddress(user.getAddress());
		userInfor.setAvartar(user.getAvatar().getSource());
		userInfor.setPhone(user.getPhone());
		return userInfor;
	}
}
