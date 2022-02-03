package ru.clevertec.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    private Long id;
    private final String name;
    private double price;

    public double getTotal(int count) {
        double total = this.price * count;
        if (count > 5) {
            total -= total * 0.1;
        }

        return total;
    }
}
