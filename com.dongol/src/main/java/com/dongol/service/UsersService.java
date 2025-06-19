package com.dongol.service;

import com.dongol.entity.Users;

public interface UsersService {
	void signUp(Users user);
	Users getUser(String username);
	boolean validate(String password, String password2);
	
}