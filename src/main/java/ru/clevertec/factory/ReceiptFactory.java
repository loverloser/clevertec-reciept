package ru.clevertec.factory;


import ru.clevertec.entity.DiscountCard;
import ru.clevertec.entity.Product;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

public final class ReceiptFactory {
    public static void writeHeader() {
        String header =
                String.format("CASH RECEIPT\n" +
                              "supermarket 123\n" +
                              "123, Green Berlin/Germany\n" +
                              "tel: 123-45-67\n" +
                              "date: %s\n" +
                              "time: %s\n", LocalDate.now(), LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

        System.out.println(header);

    }

    public static void writeBody(Map<Product, Integer> products) {
        String lineAndTitle = String.format("------------------------------------------------\n" +
                                            "%-5s%-17s%-15s%-11s%n", "QTY", "Description", "Price", "Total");

        System.out.println(lineAndTitle);

        for (Map.Entry<Product, Integer> productIntegerEntry : products.entrySet()) {
            if (productIntegerEntry.getKey() != null) {
                String temp = String.format("%-5d%-17s$%-14.2f$%-11.2f%n", productIntegerEntry.getKey().getId(),
                        productIntegerEntry.getKey().getName(),
                        productIntegerEntry.getKey().getPrice(),
                        productIntegerEntry.getKey().getTotal(productIntegerEntry.getValue()));
                System.out.println(temp);
            }
        }
        System.out.println("------------------------------------------------\n");


    }

    public static void writeFooter(Map<Product, Integer> products, DiscountCard discountCard) {
//        double total = products.entrySet().stream()
//                .mapToDouble(entry -> entry.getKey().getTotal(entry.getValue()))
//                .sum();
        double total = 0;
        for (Map.Entry<Product, Integer> productIntegerEntry : products.entrySet()) {
            total += productIntegerEntry.getKey().getTotal(productIntegerEntry.getValue());
        }
        double discount = 0;
        if (Objects.nonNull(discountCard)) {
            discount = discountCard.getDiscount() * total;
        }
        double val = total * 0.17;
        String taxableForPrint = String.format("%-37s$%-2.2f%n", "Taxable", total);
        String valForPrint = String.format("%-37s$%-2.2f%n", "Val17%", val);
        String discountForPrint = String.format("%-37s$%-2.2f%n", "Discount by card", discount);
        String totalForPrint = String.format("%-37s$%-2.2f%n", "TOTAL", total + val - discount);

        printFooter(taxableForPrint, valForPrint, discountForPrint, totalForPrint);
    }

    private static void printFooter(String taxableForPrint, String valForPrint, String discountForPrint,
                                    String totalForPrint) {
        System.out.println(taxableForPrint);
        System.out.println(valForPrint);
        System.out.println(discountForPrint);
        System.out.println(totalForPrint);
    }


}
