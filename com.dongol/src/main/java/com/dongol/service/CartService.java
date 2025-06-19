package com.dongol.service;

import java.util.List;

import com.dongol.entity.Cart;
import com.dongol.entity.CartItem;



public interface CartService {
	void addCart(Cart cart);

	void clearCart(String username);

	List<CartItem> getItems(String username);
	
	List<CartItem> cloneItems(String username);
	
}
