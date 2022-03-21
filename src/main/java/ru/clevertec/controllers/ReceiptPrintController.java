package ru.clevertec.controllers;

import com.google.gson.Gson;
import ru.clevertec.entity.DiscountCard;
import ru.clevertec.entity.Product;
import ru.clevertec.factory.DiscountCardFactory;
import ru.clevertec.factory.ProductFactory;
import ru.clevertec.print.impl.ReceiptPDFPrinter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "ReceiptPrintController", value = "/receipt/print")
public class ReceiptPrintController extends HttpServlet {

    private final ReceiptPDFPrinter pdfPrinter;

    public ReceiptPrintController() {
        this.pdfPrinter = new ReceiptPDFPrinter();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] parameterValues = request.getParameter("params").split(" ");
        Map<Product, Integer> products = ProductFactory.getInstance(parameterValues);
        DiscountCard discountCard = DiscountCardFactory.getInstance(parameterValues);
        String print = pdfPrinter.print(products, discountCard);
        try (PrintWriter writer = response.getWriter()) {
            String json = new Gson().toJson(print);
            writer.write(json);
            response.setStatus(200);
        }
    }

}
