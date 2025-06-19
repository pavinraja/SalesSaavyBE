package com.dongol.controller;

import com.dongol.entity.Orders;
import com.dongol.entity.Users;
import com.dongol.repository.OrderRepository;
import com.dongol.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin("*")
public class OrderController {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private UsersRepository userRepo;

    @GetMapping("/getOrders/{username}")
    public List<Orders> getFilteredOrdersByUser(@PathVariable String username) {
        Users user = userRepo.findByUsername(username);
        if (user == null) throw new RuntimeException("User not found");

        return orderRepo.findByUser(user).stream()
                .filter(order -> !"CREATED".equalsIgnoreCase(order.getStatus()))
                .sorted((a, b) -> b.getOrderTime().compareTo(a.getOrderTime()))
                .toList();
    }

    @PutMapping("/requestReturn/{orderId}")
    public String requestReturn(@PathVariable String orderId) {
        Orders order = orderRepo.findById(orderId).orElse(null);
        if (order == null) return "Order not found";

        if (!"DELIVERED".equalsIgnoreCase(order.getStatus())) {
            return "Order not delivered yet";
        }

        order.setStatus("RETURN_REQUESTED");
        orderRepo.save(order);
        return "Return requested successfully";
    }

    @GetMapping("/admin/orders")
    public List<Orders> getAllOrdersForAdmin() {
        return orderRepo.findAll().stream()
                .filter(order -> !"CREATED".equalsIgnoreCase(order.getStatus()))
                .sorted((a, b) -> b.getOrderTime().compareTo(a.getOrderTime()))
                .toList();
    }

    @PutMapping("/admin/orders/{orderId}/status")
    public String updateOrderStatus(@PathVariable String orderId, @RequestBody String newStatus) {
        Orders order = orderRepo.findById(orderId).orElse(null);
        if (order == null) return "Order not found";

        order.setStatus(newStatus.replace("\"", "")); // Clean quotes

        if ("DELIVERED".equalsIgnoreCase(order.getStatus())) {
            order.setDeliveryTime(LocalDateTime.now());
        }

        orderRepo.save(order);
        return "Order status updated";
    }
}