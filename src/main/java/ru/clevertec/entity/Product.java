package ru.clevertec.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    private Long id;
    private String name;
    private double price;
    private ProductProducer productProducer;

    public double getTotal(int count) {
        double total = this.price * count;
        if (count > 5) {
            total -= total * 0.1;
        }

        return total;
    }
}
