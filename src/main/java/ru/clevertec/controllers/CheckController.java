package ru.clevertec.controllers;

import ru.clevertec.ecxeptions.CardNotFoundException;
import ru.clevertec.ecxeptions.ProductNotFoundException;
import ru.clevertec.entity.DiscountCard;
import ru.clevertec.entity.Product;
import ru.clevertec.factories.ReceiptFactory;
import ru.clevertec.fill.Fill;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "CheckController", value = "/check")
public class CheckController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<Integer, Product> allProducts = Fill.getAllProducts();
        String[] itemIds = request.getParameterValues("itemId");
        Map<Integer, Product> products = getReceiptProducts(allProducts, itemIds);
        String card = request.getParameter("card");
        List<DiscountCard> discountCards = Fill.getAllDiscountCards();
        DiscountCard discountCard = null;
        try {
            discountCard = DiscountCardFactory.getInstance(card, discountCards);

        } catch (CardNotFoundException e) {
            e.printStackTrace();
        }

        printReceipt(products, discountCard);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }


    public Map<Integer, Product> getReceiptProducts(Map<Integer, Product> allProducts, String[] itemIds) {
        Map<Integer, Product> result = new HashMap<>();
        for (String s : itemIds) {
            int id = Integer.parseInt(s);
            Product product = allProducts.get(id);
            if (Objects.nonNull(product)) {
                if (result.containsKey(product.getId())) {
                    product.setCount(product.getCount() + 1);
                } else {
                    product.setCount(1);
                }
                result.put(id, product);
            } else {
                try {
                    throw new ProductNotFoundException();
                } catch (ProductNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }


    public void printReceipt(Map<Integer, Product> products, DiscountCard discountCard) {
        ReceiptFactory.writeHeader();
        ReceiptFactory.writeBody(products);
        ReceiptFactory.writeFooter(products, discountCard);
    }


}
