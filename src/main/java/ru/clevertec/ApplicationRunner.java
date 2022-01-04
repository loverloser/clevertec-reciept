package ru.clevertec;

import ru.clevertec.ecxeptions.CardNotFoundException;
import ru.clevertec.ecxeptions.ProductNotFoundException;
import ru.clevertec.entity.DiscountCard;
import ru.clevertec.entity.Product;
import ru.clevertec.factories.DiscountCardFactory;
import ru.clevertec.factories.ProductFactory;
import ru.clevertec.factories.ReceiptFactory;
import ru.clevertec.fill.Fill;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ApplicationRunner {
    public static void main(String[] args) {
        DiscountCard discountCard = getDiscountCard(args);
        Map<Integer, Product> allProducts = Fill.getAllProducts();
        Map<Integer, Product> result = getProductsInReceipt(args, allProducts);

        ReceiptFactory.writeHeader();
        ReceiptFactory.writeBody(result);
        ReceiptFactory.writeFooter(result, discountCard);

    }

    public static DiscountCard getDiscountCard(String[] args) {
        String cardName = "";
        DiscountCard discountCard = null;
        for (String s : args) {
            if (s.contains("card-")) {
                cardName = s;
            }
        }
        try {
            discountCard = DiscountCardFactory.getInstance(cardName, Fill.getAllDiscountCards());
        } catch (CardNotFoundException e) {
            e.printStackTrace();
        }

        return discountCard;
    }


    //get product which will be in receipt
    public static Map<Integer, Product> getProductsInReceipt(String[] params, Map<Integer, Product> products) {
        Map<Integer, Product> result = new HashMap<>();
        Product product = null;
        for (String line : params) {
            try {
                product = ProductFactory.getInstance(line, products);
            } catch (ProductNotFoundException e) {
                e.printStackTrace();
            }
            if (Objects.nonNull(product)) {
                result.put(product.getId(), product);
            }
        }

        return result;
    }

}
