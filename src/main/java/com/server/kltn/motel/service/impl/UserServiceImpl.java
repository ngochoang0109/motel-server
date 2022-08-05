package com.server.kltn.motel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
