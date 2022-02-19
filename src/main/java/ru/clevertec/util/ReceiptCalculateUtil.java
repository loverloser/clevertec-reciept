package ru.clevertec.util;

import ru.clevertec.entity.DiscountCard;
import ru.clevertec.entity.Product;

import java.util.Map;
import java.util.Objects;

public final class ReceiptCalculateUtil {

    private static final double VAL = 0.17;
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static ReceiptCalculateUtil receiptCalculate;

    private ReceiptCalculateUtil() {
    }

    public static double getTotal(Map<Product, Integer> products) {
        return products.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getTotal(entry.getValue()))
                .sum();
    }

    public static double getDiscount(DiscountCard discountCard, double total) {
        return Objects.nonNull(discountCard) ? discountCard.getDiscount() * total : 0;
    }

}
