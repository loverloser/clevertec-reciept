package ru.clevertec.factories;

import ru.clevertec.entity.DiscountCard;
import ru.clevertec.service.impl.DiscountCardServiceImpl;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DiscountCardFactory {

    private static final String discountCardRegex = "card-(\\d+)";
    private static final DiscountCardServiceImpl discountCardService = new DiscountCardServiceImpl();

    public static DiscountCard getInstance(String[] args) {
        String card = Arrays.stream(args)
                .filter(s -> s.matches(discountCardRegex))
                .findFirst()
                .orElse("");

        Pattern pattern = Pattern.compile(discountCardRegex);
        Matcher matcher = pattern.matcher(card);
        Long idDiscountCard = null;
        if (matcher.find()){
            String id = matcher.group(1);
            idDiscountCard = Long.parseLong(id);
        }

        return discountCardService.findById(idDiscountCard);
    }
}
