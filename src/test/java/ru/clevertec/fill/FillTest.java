package ru.clevertec.fill;

import ru.clevertec.entity.DiscountCard;
import ru.clevertec.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class FillTest {
    private Map<Integer, Product> products;
    private List<DiscountCard> discountCards;

    @BeforeEach
    void prepare() {
        products = new HashMap<>();
        discountCards = new ArrayList<>();
    }

    @Test
    void getAllProductsSize() {
        products = Fill.getAllProducts();
        assertThat(products).hasSize(9);
    }

    @Test
    void getAllDiscountCardsSize() {
        discountCards = Fill.getAllDiscountCards();
        assertThat(discountCards).hasSize(4);
    }
}