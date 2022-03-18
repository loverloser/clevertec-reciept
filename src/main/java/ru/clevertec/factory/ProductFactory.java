package ru.clevertec.factory;

import ru.clevertec.ecxeption.ProductNotFoundException;
import ru.clevertec.entity.Product;
import ru.clevertec.repository.impl.ProductRepositoryImpl;
import ru.clevertec.service.impl.ProductServiceImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class ProductFactory {

    private static final String productRegex = "(\\d+)-(\\d+)";
    private static final ProductServiceImpl productService = new ProductServiceImpl(new ProductRepositoryImpl());

    public static Map<Product, Integer> getInstance(String[] args) {
        Map<Product, Integer> products = new HashMap<>();
        List<String> productsValues = Arrays.stream(args)
                .filter(s -> s.matches(productRegex))
                .collect(Collectors.toList());

        Pattern pattern = Pattern.compile(productRegex);
        for (String productsValue : productsValues) {
            Matcher matcher = pattern.matcher(productsValue);
            if (matcher.find()) {
                int count = Integer.parseInt(matcher.group(2));
                try {
                    Product product = productService
                            .findById(matcher.group(1))
                            .orElseThrow(ProductNotFoundException::new);
                    products.put(product, count);
                } catch (ProductNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return products;
    }
}
