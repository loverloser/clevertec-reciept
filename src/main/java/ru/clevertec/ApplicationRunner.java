//package ru.clevertec;
//
//import ru.clevertec.ecxeptions.CardNotFoundException;
//import ru.clevertec.entity.DiscountCard;
//import ru.clevertec.entity.Product;
//import ru.clevertec.factories.DiscountCardFactory;
//import ru.clevertec.factories.ReceiptFactory;
//
//import java.util.Map;
//
//public class ApplicationRunner {
//    public static void main(String[] args) {
//        DiscountCard discountCard = getDiscountCard(args);
//        Map<Long, Product> allProducts = Fill.getAllProducts();
//        Map<Long, Product> result = getProductsInReceipt(args, allProducts);
//
//        ReceiptFactory.writeHeader();
//        ReceiptFactory.writeBody(result);
//        ReceiptFactory.writeFooter(result, discountCard);
//
//    }
//
//    public static DiscountCard getDiscountCard(String[] args) {
//        String cardName = "";
//        DiscountCard discountCard = null;
//        for (String s : args) {
//            if (s.contains("card-")) {
//                cardName = s;
//            }
//        }
//        try {
//            discountCard = DiscountCardFactory.getInstance(cardName, Fill.getAllDiscountCards());
//        } catch (CardNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        return discountCard;
//    }
//
//}
