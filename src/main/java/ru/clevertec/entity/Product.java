package ru.clevertec.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    private Long id;
    private final String name;
    private double price;

    public Product(Long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public double getTotal(int count) {
        double total = this.price * count;
        if (count > 5) {
            total -= total * 0.1;
        }

        return total;
    }
}
