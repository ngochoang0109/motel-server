package com.server.kltn.motel.service;

import com.server.kltn.motel.entity.User;

public interface UserService {
	User findUserByUsernameOrEmail(String username, String email);
}
