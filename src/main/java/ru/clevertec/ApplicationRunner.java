package ru.clevertec;

import ru.clevertec.entity.DiscountCard;
import ru.clevertec.entity.Product;
import ru.clevertec.factories.DiscountCardFactory;
import ru.clevertec.factories.ProductFactory;
import ru.clevertec.factories.ReceiptFactory;

import java.util.Map;

public class ApplicationRunner {
    public static void main(String[] args) {
        DiscountCard discountCard = DiscountCardFactory.getInstance(args);
        Map<Product, Integer> products = ProductFactory.getInstance(args);

        ReceiptFactory.writeHeader();
        ReceiptFactory.writeBody(products);
        ReceiptFactory.writeFooter(products, discountCard);

    }

}
