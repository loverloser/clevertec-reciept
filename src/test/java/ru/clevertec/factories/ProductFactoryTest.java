package ru.clevertec.factories;


import ru.clevertec.ecxeptions.ProductNotFoundException;
import ru.clevertec.entity.Product;
import ru.clevertec.fill.Fill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductFactoryTest {
    private final String incorrectProductIdLine = "0-20";
    private Map<Integer, Product> products;

    @BeforeEach
    void prepare() {
        products = Fill.getAllProducts();
    }


    @Test
    void throwsExceptionIfProductNameIdIsNotCorrect() {
        assertThrows(ProductNotFoundException.class,
                () -> ProductFactory.getInstance(incorrectProductIdLine, products));
    }

}