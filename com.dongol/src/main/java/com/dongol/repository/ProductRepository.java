package com.dongol.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dongol.entity.Product;


public interface ProductRepository 
		extends JpaRepository<Product, Long>  {

	Product findByName(String name);

	Product findByCategory(String category);

}
