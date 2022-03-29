package ru.clevertec.print.impl;

import ru.clevertec.entity.DiscountCard;
import ru.clevertec.entity.Product;
import ru.clevertec.print.Printable;
import ru.clevertec.util.ReceiptCalculateUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class ReceiptConsolePrinter implements Printable {

    @Override
    public String print(Map<Product, Integer> products, DiscountCard discountCard) {
        writeHeader();
        writeBody(products);
        writeFooter(products, discountCard);
        return "";
    }

    public static void writeHeader() {
        String header =
                String.format("CASH RECEIPT\n" +
                              "supermarket 123\n" +
                              "123, Green Berlin/Germany\n" +
                              "tel: 123-45-67\n" +
                              "date: %s\n" +
                              "time: %s\n", LocalDate.now(),
                        LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        System.out.println(header);
    }

    public static void writeBody(Map<Product, Integer> products) {
        String lineAndTitle = String.format("------------------------------------------------\n" +
                                            "%-5s%-17s%-15s%-11s%n", "QTY", "Description", "Price", "Total");

        System.out.println(lineAndTitle);

        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            String temp = String.format("%-5d%-17s$%-14.2f$%-11.2f%n", entry.getValue(),
                    entry.getKey().getName(),
                    entry.getKey().getPrice(),
                    entry.getKey().getTotal(entry.getValue()));
            System.out.println(temp);
        }

        System.out.println("------------------------------------------------\n");
    }

    public static void writeFooter(Map<Product, Integer> products, DiscountCard discountCard) {

        double total = ReceiptCalculateUtil.getTotal(products);
        double discount = ReceiptCalculateUtil.getDiscount(discountCard, total);

        String discountForPrint = String.format("%-37s$%-2.2f%n", "Discount by card", discount);
        String totalForPrint = String.format("%-37s$%-2.2f%n", "TOTAL", total - discount);

        System.out.println(discountForPrint + '\n' + totalForPrint);
    }

}
