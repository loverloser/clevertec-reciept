package ru.clevertec.factories;

import ru.clevertec.ecxeptions.CardNotFoundException;
import ru.clevertec.entity.DiscountCard;
import ru.clevertec.fill.Fill;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DiscountCardFactoryTest {
    private final String correctCardName = "card-1234";
    private final String incorrectCardName = "card-1234q";
    private List<DiscountCard> discountCards;

    @BeforeEach
    void prepare() {
        discountCards = Fill.getAllDiscountCards();
    }

    @Test
    void throwsExceptionIfCardNameIsNotCorrect() {
        assertThrows(CardNotFoundException.class,
                () -> DiscountCardFactory.getInstance(incorrectCardName, discountCards));
    }

    @Test
    void getDiscountCardIfCardNameIsCorrect() {
        Assertions.assertDoesNotThrow(() -> DiscountCardFactory.getInstance(correctCardName, discountCards));
    }
}
