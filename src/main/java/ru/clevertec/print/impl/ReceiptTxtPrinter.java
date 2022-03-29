package ru.clevertec.print.impl;

import lombok.SneakyThrows;
import ru.clevertec.entity.DiscountCard;
import ru.clevertec.entity.Product;
import ru.clevertec.print.Printable;
import ru.clevertec.util.ReceiptCalculateUtil;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Map;

public class ReceiptTxtPrinter implements Printable {

    private static final Path FILE_PATH = Paths.get("src", "main", "resources", "receipts",
            "Receipt:" + LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))
            + ".txt");

    @SneakyThrows
    @Override
    public String print(Map<Product, Integer> products, DiscountCard discountCard) {
        File file = FILE_PATH.toFile();
        if (file.createNewFile()) {
            writeHeader(file);
            writeBody(products, file);
            writeFooter(products, discountCard, file);
        }

        return ".txt";
    }

    @SneakyThrows
    public static void writeHeader(File file) {
        String header =
                String.format("CASH RECEIPT\n" +
                              "supermarket 123\n" +
                              "123, Green Berlin/Germany\n" +
                              "tel: 123-45-67\n" +
                              "date: %s\n" +
                              "time: %s\n", LocalDate.now(),
                        LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        Files.write(file.toPath(), header.getBytes(), StandardOpenOption.APPEND);
    }

    @SneakyThrows
    public static void writeBody(Map<Product, Integer> products, File file) {
        String lineAndTitle = String.format("------------------------------------------------\n" +
                                            "%-5s%-17s%-15s%-11s%n", "QTY", "Description", "Price", "Total");

        Files.write(file.toPath(), lineAndTitle.getBytes(), StandardOpenOption.APPEND);

        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            String temp = String.format("%-5d%-17s$%-14.2f$%-11.2f%n", entry.getValue(),
                    entry.getKey().getName(),
                    entry.getKey().getPrice(),
                    entry.getKey().getTotal(entry.getValue()));
            Files.write(file.toPath(), temp.getBytes(), StandardOpenOption.APPEND);
        }

        Files.write(file.toPath(), "------------------------------------------------\n".getBytes(),
                StandardOpenOption.APPEND);
    }

    @SneakyThrows
    public static void writeFooter(Map<Product, Integer> products, DiscountCard discountCard, File file) {

        double total = ReceiptCalculateUtil.getTotal(products);
        double discount = ReceiptCalculateUtil.getDiscount(discountCard, total);

        String discountForPrint = String.format("%-37s$%-2.2f%n", "Discount by card", discount);
        String totalForPrint = String.format("%-37s$%-2.2f%n", "TOTAL", total - discount);


        String result = discountForPrint + '\n' + totalForPrint;

        Files.write(file.toPath(), result.getBytes(), StandardOpenOption.APPEND);
    }
}
