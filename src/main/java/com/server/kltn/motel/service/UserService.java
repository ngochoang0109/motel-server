package com.server.kltn.motel.service;
import java.util.List;

import com.server.kltn.motel.api.authentication.payload.UserInfor;
import com.server.kltn.motel.entity.User;

public interface UserService {
	User findUserByUsernameOrEmail(String username, String email);
	UserInfor getCurrentUser(String username);
	List<UserInfor> getAllUser();
}
