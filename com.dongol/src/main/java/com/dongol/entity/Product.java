package com.dongol.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String name;
    String description;
    int price;
    String photo;
    String category;

    // ✅ Store all individual ratings
    @ElementCollection
    private List<Integer> ratings = new ArrayList<>();

    // ✅ Constructors
    public Product() {}

    public Product(Long id, String name, String description, int price,
                   String photo, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.photo = photo;
        this.category = category;
    }

    // ✅ Getters & Setters
    public Long getId()                  { return id; }
    public void setId(Long id)           { this.id = id; }

    public String getName()              { return name; }
    public void setName(String name)     { this.name = name; }

    public String getDescription()       { return description; }
    public void setDescription(String d) { this.description = d; }

    public int getPrice()                { return price; }
    public void setPrice(int price)      { this.price = price; }

    public String getPhoto()             { return photo; }
    public void setPhoto(String photo)   { this.photo = photo; }

    public String getCategory()          { return category; }
    public void setCategory(String c)    { this.category = c; }

    public List<Integer> getRatings()    { return ratings; }
    public void setRatings(List<Integer> ratings) { this.ratings = ratings; }

    // ✅ Average rating calculated dynamically
    @Transient
    public double getRating() {
        if (ratings == null || ratings.isEmpty()) return 0.0;
        return ratings.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", description=" + description +
               ", price=" + price + ", photo=" + photo + ", category=" + category +
               ", avgRating=" + getRating() + "]";
    }
}
