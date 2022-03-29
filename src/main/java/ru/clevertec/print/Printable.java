package ru.clevertec.print;

import ru.clevertec.entity.DiscountCard;
import ru.clevertec.entity.Product;

import java.util.Map;

public interface Printable {

    String print(Map<Product, Integer> products, DiscountCard discountCard);
}
