package ru.clevertec;

import ru.clevertec.entity.DiscountCard;
import ru.clevertec.entity.Product;
import ru.clevertec.factory.DiscountCardFactory;
import ru.clevertec.factory.ProductFactory;
import ru.clevertec.print.impl.ReceiptConsolePrinter;
import ru.clevertec.print.impl.ReceiptPDFPrinter;
import ru.clevertec.print.impl.ReceiptTxtPrinter;
import ru.clevertec.repository.impl.ProductRepositoryImpl;
import ru.clevertec.service.impl.ProductServiceImpl;

import java.util.Map;

public class ApplicationRunner {
    public static void main(String[] args) {
        ProductServiceImpl productService = new ProductServiceImpl(new ProductRepositoryImpl());
        System.out.println(productService.getAll());
        DiscountCard discountCard = DiscountCardFactory.getInstance(args);
        Map<Product, Integer> products = ProductFactory.getInstance(args);
      
        ReceiptPDFPrinter receiptPDFPrinter = new ReceiptPDFPrinter();
        receiptPDFPrinter.print(products, discountCard);

        ReceiptConsolePrinter receiptConsolePrinter = new ReceiptConsolePrinter();
        receiptConsolePrinter.print(products, discountCard);

        ReceiptTxtPrinter receiptTxtPrinter = new ReceiptTxtPrinter();
        receiptTxtPrinter.print(products, discountCard);
    }

}
