package ru.clevertec.factories;

import ru.clevertec.ecxeptions.ProductNotFoundException;
import ru.clevertec.entity.Product;
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
    private static final ProductServiceImpl productService = new ProductServiceImpl();

    public static Map<Product, Integer> getInstance(String[] args) throws ProductNotFoundException {
        Map<Product,Integer> products = new HashMap<>();
        List<String> productsValues = Arrays.stream(args)
                .filter(s -> s.matches(productRegex))
                .collect(Collectors.toList());

        Pattern pattern = Pattern.compile(productRegex);
        for (String productsValue : productsValues) {
            Matcher matcher = pattern.matcher(productsValue);
            if(matcher.find()){
                Product product = productService.getProduct(Long.parseLong(matcher.group(1)));
                if (product == null){
                    throw new ProductNotFoundException();
                }
                int count = Integer.parseInt(matcher.group(2));
                products.put(product, count);
            }
        }
        return products;
    }
}
