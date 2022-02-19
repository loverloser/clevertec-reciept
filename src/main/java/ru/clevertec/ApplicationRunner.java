package ru.clevertec;

import lombok.SneakyThrows;
import ru.clevertec.entity.DiscountCard;
import ru.clevertec.entity.Product;
import ru.clevertec.factories.DiscountCardFactory;
import ru.clevertec.factories.ProductFactory;

import java.util.Map;

public class ApplicationRunner {
    @SneakyThrows
    public static void main(String[] args) {
        DiscountCard discountCard = DiscountCardFactory.getInstance(args);
        Map<Product, Integer> products = ProductFactory.getInstance(args);
    }

}
