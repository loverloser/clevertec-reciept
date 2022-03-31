package ru.clevertec.factory;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.clevertec.ecxeption.CardNotFoundException;
import ru.clevertec.entity.DiscountCard;
import ru.clevertec.service.interfaces.DiscountCardService;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Component
public final class DiscountCardFactory {

    private static final String DISCOUNT_CARD_REGEX = "card-(\\d+)";
    private final DiscountCardService discountCardService;


    @SneakyThrows
    public DiscountCard getInstance(String[] args) {
        String card = Arrays.stream(args)
                .filter(s -> s.matches(DISCOUNT_CARD_REGEX))
                .findFirst()
                .orElse("");

        Pattern pattern = Pattern.compile(DISCOUNT_CARD_REGEX);
        Matcher matcher = pattern.matcher(card);
        String discountCardId = null;
        if (matcher.find()) {
            discountCardId = matcher.group(1);
        }

        return discountCardService
                .findById(Long.parseLong(discountCardId))
                .orElseThrow(CardNotFoundException::new);
    }
}
