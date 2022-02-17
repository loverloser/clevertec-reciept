package ru.clevertec.util;

import ru.clevertec.entity.DiscountCard;
import ru.clevertec.entity.Product;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

public final class ReceiptCalculateUtil {

    private static final double VAL = 0.17;

    public static final String CROSS_LINE = "------------------------------------------------\n";
    public static final String TIME_FORMAT = "HH:mm:ss";

    private ReceiptCalculateUtil() {
    }

    public static String getReceipt(Map<Product, Integer> products, DiscountCard discountCard){
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder
                .append(writeHeader())
                .append(writeBody(products))
                .append(writeFooter(products, discountCard))
                .toString();
    }

    private static String writeHeader() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append("CASH RECEIPT\n")
                .append("supermarket 123\n")
                .append("123, Green Berlin/Germany\n")
                .append("tel: 123-45-67\n")
                .append(String.format("date: %s\n", LocalDate.now()))
                .append(String.format("time: %s\n", LocalTime.now().format(DateTimeFormatter.ofPattern(TIME_FORMAT))))
                .append(CROSS_LINE)
                .append(String.format("%-5s%-17s%-15s%-11s%n", "QTY", "Description", "Price", "Total\n"))
                .toString();
    }

    private static String writeBody(Map<Product, Integer> products) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            if (entry.getKey() != null) {
                double totalPriceOfProduct = entry.getKey().getTotal(entry.getValue());
                String temp = String.format("%-5d%-17s$%-14.2f$%-11.2f%n\n",
                        entry.getValue(),
                        entry.getKey().getName(),
                        entry.getKey().getPrice(), totalPriceOfProduct
                );

                stringBuilder.append(temp);
            }
        }
        return stringBuilder.append(CROSS_LINE).toString();

    }

    private static String writeFooter(Map<Product, Integer> products, DiscountCard discountCard) {
        StringBuilder stringBuilder = new StringBuilder();

        double total = getTotal(products);
        double discount = getDiscount(discountCard, total);
        double val = total * VAL;

        String taxableForPrint = String.format("%-37s$%-2.2f%n\n", "Taxable", total);
        String valForPrint = String.format("%-37s$%-2.2f%n\n", "Val17%", val);
        String discountForPrint = String.format("%-37s$%-2.2f%n\n", "Discount by card", discount);
        String totalForPrint = String.format("%-37s$%-2.2f%n\n", "TOTAL", total + val - discount);

        return stringBuilder.append(taxableForPrint)
                .append(valForPrint)
                .append(discountForPrint)
                .append(totalForPrint)
                .toString();

    }


    private static double getTotal(Map<Product, Integer> products) {
        return products.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getTotal(entry.getValue()))
                .sum();
    }

    private static double getDiscount(DiscountCard discountCard, double total) {
        double discount = 0;
        if (Objects.nonNull(discountCard)) {
            discount = discountCard.getDiscount() * total;
        }

        return discount;
    }

}
