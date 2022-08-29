package com.server.kltn.motel.service;

import com.server.kltn.motel.api.authentication.payload.NewUserDataset;
import com.server.kltn.motel.common.MessageCommon;

public interface AuthenService {
	MessageCommon registerUser(NewUserDataset newUserDataset);
//	UserDto findUserByUsernameOrEmail(String username, String email);
}
