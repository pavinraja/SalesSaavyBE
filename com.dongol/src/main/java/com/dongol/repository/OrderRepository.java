package com.dongol.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dongol.entity.Orders;
import com.dongol.entity.Users;



public interface OrderRepository extends JpaRepository<Orders, String> {
    List<Orders> findByUser(Users user);
    List<Orders> findByUserAndStatus(Users user, String status);
}
