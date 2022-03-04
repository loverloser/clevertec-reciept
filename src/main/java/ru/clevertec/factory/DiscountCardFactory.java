package ru.clevertec.factory;

import lombok.SneakyThrows;
import ru.clevertec.ecxeption.CardNotFoundException;
import ru.clevertec.entity.DiscountCard;
import ru.clevertec.repository.impl.DiscountCardRepositoryImpl;
import ru.clevertec.service.impl.DiscountCardServiceImpl;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DiscountCardFactory {

    private static final String DISCOUNT_CARD_REGEX = "card-(\\d+)";
    private static final DiscountCardServiceImpl DISCOUNT_CARD_SERVICE =
            new DiscountCardServiceImpl(new DiscountCardRepositoryImpl());

    @SneakyThrows
    public static DiscountCard getInstance(String[] args) {
        String card = Arrays.stream(args)
                .filter(s -> s.matches(DISCOUNT_CARD_REGEX))
                .findFirst()
                .orElse("");

        Pattern pattern = Pattern.compile(DISCOUNT_CARD_REGEX);
        Matcher matcher = pattern.matcher(card);
        Long idDiscountCard = null;
        if (matcher.find()) {
            String id = matcher.group(1);
            idDiscountCard = Long.parseLong(id);
        }

        return DISCOUNT_CARD_SERVICE
                .findById(idDiscountCard)
                .orElseThrow(CardNotFoundException::new);
    }
}
