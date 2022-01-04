package ru.clevertec.fill;

import ru.clevertec.entity.DiscountCard;
import ru.clevertec.entity.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fill {
    //get products from file and put it in products(Map<Integer, Product>)
    public static Map<Integer, Product> getAllProducts() {
        Map<Integer, Product> products = new HashMap<>();
        products.put(1, new Product(1, "Lemon", 12.34));
        products.put(2, new Product(2, "Melon", 9.99));
        products.put(3, new Product(3, "Milk", 1.34));
        products.put(4, new Product(4, "Potato", 2.37));
        products.put(5, new Product(5, "Chicken", 3.22));
        products.put(6, new Product(6, "Water", 0.99));
        products.put(7, new Product(7, "Juice", 2.10));
        products.put(8, new Product(8, "Cake", 5.99));
        products.put(9, new Product(9, "Lime", 1.22));


        return products;
    }

    public static List<DiscountCard> getAllDiscountCards() {
        List<DiscountCard> discountCards = new ArrayList<>();
        discountCards.add(new DiscountCard("card-34432", 0.1));
        discountCards.add(new DiscountCard("card-1234", 0.2));
        discountCards.add(new DiscountCard("card-3344", 0.15));
        discountCards.add(new DiscountCard("card-6564", 0.3));

        return discountCards;
    }
}
