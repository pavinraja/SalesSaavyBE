package com.dongol.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.dongol.dto.CartItemDTO;
import com.dongol.entity.Cart;
import com.dongol.entity.CartItem;
import com.dongol.entity.Product;
import com.dongol.entity.Users;
import com.dongol.service.CartService;
import com.dongol.service.ProductService;
import com.dongol.service.UsersService;

@CrossOrigin("*")
@RestController
public class ProductController {

    @Autowired
    ProductService service;

    @Autowired
    UsersService uService;

    @Autowired
    CartService cService;

    @PostMapping("/addProduct")
    public String addProduct(@RequestBody Product product) {
        return service.addProduct(product);
    }

    @GetMapping("/searchProduct")
    public Product searchProduct(@RequestParam long id) {
        return service.searchProduct(id);
    }

    @PostMapping("/updateProduct")
    public String updateProduct(@RequestBody Product product) {
        return service.updateProduct(product);
    }

    @GetMapping("/deleteProduct")
    public String deleteProduct(@RequestParam long id) {
        return service.deleteProduct(id);
    }

    @GetMapping("/getAllProducts")
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }

    @PostMapping("/addToCart")
    public String addToCart(@RequestBody CartItemDTO dto) {
        Users user = uService.getUser(dto.getUsername());
        if (user == null) return "user not found";

        Product product = service.searchProduct(dto.getProductId());
        if (product == null) return "product not found";

        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            user.setCart(cart);
            cService.addCart(cart);
        }

        List<CartItem> items = cart.getCartItems();
        if (items == null) items = new ArrayList<>();

        boolean found = false;
        for (CartItem ci : items) {
            if (ci.getProduct() != null && ci.getProduct().getId().equals(product.getId())) {
                ci.setQuantity(ci.getQuantity() + dto.getQuantity());
                found = true;
                break;
            }
        }

        if (!found) {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(dto.getQuantity());
            items.add(newItem);
        }

        cart.setCartItems(items);
        cService.addCart(cart);
        return "cart added";
    }

    @GetMapping("/getCart/{username}")
    public List<CartItem> getCart(@PathVariable String username) {
        Users u = uService.getUser(username);
        if (u == null || u.getCart() == null) return new ArrayList<>();

        List<CartItem> cartItems = u.getCart().getCartItems();
        if (cartItems == null) return new ArrayList<>();

        return cartItems.stream()
                .filter(item -> item.getProduct() != null)
                .toList();
    }

    @PostMapping("/cart/updateQuantity")
    public List<CartItem> updateQuantity(@RequestBody CartItemDTO dto) {
        Users user = uService.getUser(dto.getUsername());
        if (user == null || user.getCart() == null) return new ArrayList<>();

        List<CartItem> items = user.getCart().getCartItems();
        if (items == null) return new ArrayList<>();

        Iterator<CartItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            CartItem item = iterator.next();
            Product product = item.getProduct();
            if (product != null && product.getId().equals(dto.getProductId())) {
                int newQty = item.getQuantity() + dto.getQuantity();
                if (newQty <= 0) {
                    iterator.remove();
                } else {
                    item.setQuantity(newQty);
                }
                break;
            }
        }

        cService.addCart(user.getCart());
        return items.stream()
                .filter(i -> i.getProduct() != null)
                .toList();
    }

    @PostMapping("/cart/removeItem")
    public List<CartItem> removeItem(@RequestBody CartItemDTO dto) {
        Users user = uService.getUser(dto.getUsername());
        if (user == null || user.getCart() == null) return new ArrayList<>();

        List<CartItem> items = user.getCart().getCartItems();
        if (items == null) return new ArrayList<>();

        items.removeIf(item ->
                item.getProduct() != null && item.getProduct().getId().equals(dto.getProductId())
        );

        cService.addCart(user.getCart());
        return items.stream()
                .filter(i -> i.getProduct() != null)
                .toList();
    }

    // ✅ New rating system: stores individual ratings in list
    @PutMapping("/products/{productId}/rate")
    public String rateProduct(@PathVariable Long productId, @RequestBody Map<String, Integer> payload) {
        Integer rating = payload.get("rating");
        if (rating == null || rating < 1 || rating > 5) {
            return "Invalid rating. Must be between 1 and 5.";
        }

        Product product = service.searchProduct(productId);
        if (product == null) {
            return "Product not found";
        }

        List<Integer> ratings = product.getRatings();
        if (ratings == null) {
            ratings = new ArrayList<>();
        }
        ratings.add(rating);
        product.setRatings(ratings);

        service.addProduct(product); // save updated product
        return "Thank you for your rating!";
    }
}