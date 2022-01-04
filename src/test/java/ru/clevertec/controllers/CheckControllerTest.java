package ru.clevertec.controllers;

import ru.clevertec.entity.Product;
import ru.clevertec.fill.Fill;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CheckControllerTest {
    private CheckController checkController;
    private Map<Integer, Product> products;
    private final String[] ids = {"1", "2"};
    private Map<Integer, Product> receiptProducts;
    private static final Product lemon = new Product(1, "Lemon", 12.34, 1);
    private static final Product melon = new Product(2, "Melon", 9.99, 1);


    @BeforeEach
    void prepare() {
        checkController = new CheckController();
        products = Fill.getAllProducts();
        receiptProducts = checkController.getReceiptProducts(products, ids);
    }

    @Test
    void getReceiptProductsSize() {
        assertThat(receiptProducts).hasSize(2);
    }


    @Test
    void getProductsKeysAndValues() {
        Assertions.assertAll(
                () -> assertThat(receiptProducts).containsKeys(1, 2),
                () -> assertThat(receiptProducts).containsValues(lemon, melon)
        );

    }

}
