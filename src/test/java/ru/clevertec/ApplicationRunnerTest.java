package ru.clevertec;

import ru.clevertec.entity.DiscountCard;
import ru.clevertec.entity.Product;
import ru.clevertec.fill.Fill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ApplicationRunnerTest {
    private final String[] args = {"3-1", "2-5", "5-1", "6-1", "card-1234"};
    private Map<Integer, Product> products;
    private static final Product melon = new Product(2, "Melon", 9.99, 5);
    private static final Product water = new Product(6, "Water", 0.99, 1);
    private static final Product lime = new Product(9, "Lime", 1.22);
    private static final DiscountCard discountCard = new DiscountCard("card-1234", 0.2);


    @BeforeEach
    void prepare() {
        products = Fill.getAllProducts();
    }

    @Test
    void getReceiptProductsSizeKeysAndValues() {
        Map<Integer, Product> result = ApplicationRunner.getProductsInReceipt(args, products);
        assertAll(
                () -> assertThat(result).hasSize(4),
                () -> assertThat(result).containsKeys(3, 2, 5, 6),
                () -> assertThat(result).containsValues(melon, water),
                () -> assertThat(result).doesNotContainKeys(1, 9),
                () -> assertThat(result).doesNotContainValue(lime)
        );
    }

    @Test
    void getCardByName() {
        DiscountCard card = ApplicationRunner.getDiscountCard(args);
        assertThat(card).isEqualTo(discountCard);
    }


}