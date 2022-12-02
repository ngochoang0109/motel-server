package com.server.kltn.motel.service.impl;

import java.util.LinkedList;
import java.util.List;

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
		userInfor.setAddress(user.getAddress()==null?"Chưa cập nhật địa chỉ":user.getAddress());
		userInfor.setAvartar(user.getAvatar()==null?"":user.getAvatar().getSource());
		userInfor.setPhone(user.getPhone());
		userInfor.setUsername(user.getUsername());
		userInfor.setEmail(user.getEmail()==null?"Chưa cập nhật địa chỉ":user.getEmail());
		return userInfor;
	}
	
	@Override
	public List<UserInfor> getAllUser() {
		List<UserInfor> userInfors = new LinkedList<>();
		List<User> users = userRepository.findAll();
		for (User user : users) {
			UserInfor userInfor= new UserInfor();
			userInfor.setId(user.getId());
			userInfor.setFullname(user.getFullname());
			userInfor.setAddress(user.getAddress()==null?"Chưa cập nhật địa chỉ":user.getAddress());
			userInfor.setAvartar(user.getAvatar()==null?"":user.getAvatar().getSource());
			userInfor.setPhone(user.getPhone());
			userInfor.setUsername(user.getUsername());
			userInfor.setEmail(user.getEmail()==null?"Chưa cập nhật địa chỉ":user.getEmail());
			userInfor.setRole(user.getRoles().get(0).getName());
			userInfors.add(userInfor);
		}
		return userInfors;
	}
}
