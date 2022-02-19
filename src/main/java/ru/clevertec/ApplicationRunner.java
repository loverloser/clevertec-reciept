package ru.clevertec;

import ru.clevertec.entity.DiscountCard;
import ru.clevertec.entity.Product;
import ru.clevertec.factories.DiscountCardFactory;
import ru.clevertec.factories.ProductFactory;
import ru.clevertec.print.impl.ReceiptPrinter;

import java.util.Map;

public class ApplicationRunner {
    public static void main(String[] args) {
        DiscountCard discountCard = DiscountCardFactory.getInstance(args);
        Map<Product, Integer> products = ProductFactory.getInstance(args);
        ReceiptPrinter receiptPrinter = new ReceiptPrinter();
        receiptPrinter.printPDF(products, discountCard);
    }

}
