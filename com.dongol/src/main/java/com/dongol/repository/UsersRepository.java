package com.dongol.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dongol.entity.Users;

public interface UsersRepository 
		extends JpaRepository<Users, Long> {
	Users findByUsername(String username);
}
