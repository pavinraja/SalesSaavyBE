package com.dongol.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dongol.entity.Cart;



public interface CartRepository 
		extends JpaRepository<Cart, Long>{

}
