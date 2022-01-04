package ru.clevertec.factories;

import ru.clevertec.ecxeptions.CardNotFoundException;
import ru.clevertec.entity.DiscountCard;

import java.util.List;
import java.util.Objects;

public final class DiscountCardFactory {
    public static DiscountCard getInstance(String cardName, List<DiscountCard> discountCards) throws CardNotFoundException {
        DiscountCard discountCard = null;
        for (DiscountCard card : discountCards) {
            if (card.getName().equals(cardName)) {
                discountCard = card;
            }
        }

        if (Objects.nonNull(cardName) && Objects.isNull(discountCard)) {
            throw new CardNotFoundException();
        }

        return discountCard;
    }
}
