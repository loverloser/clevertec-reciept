package ru.clevertec.print;

import ru.clevertec.entity.DiscountCard;
import ru.clevertec.entity.Product;

import java.io.FileNotFoundException;
import java.util.Map;

public interface Printable {
    void print(Map<Product, Integer> products, DiscountCard discountCard);
}
