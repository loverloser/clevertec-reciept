package ru.clevertec.factories;

import ru.clevertec.ecxeptions.ProductCountException;
import ru.clevertec.ecxeptions.ProductNotFoundException;
import ru.clevertec.entity.Product;

import java.util.Map;
import java.util.Objects;

public final class ProductFactory {
    public static Product getInstance(String line, Map<Integer, Product> products) throws ProductNotFoundException {
        Product product = null;
        String regex = "\\d+-\\d+";
        if (line.matches(regex)) {
            String[] split = line.split("-");
            int id = Integer.parseInt(split[0]);
            int count = Integer.parseInt(split[1]);
            if (count < 1) {
                try {
                    throw new ProductCountException(count + "is incorrect value for count");
                } catch (ProductCountException e) {
                    e.printStackTrace();
                }
            } else {
                product = products.get(id);
                if (Objects.nonNull(product)) {
                    product.setCount(count);
                } else {
                    throw new ProductNotFoundException();
                }
            }


        }


        return product;
    }
}
