package ru.clevertec.controllers;

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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;

@WebServlet(name = "ReceiptPrintController", value = "/receipt/print")
public class ReceiptPrintController extends HttpServlet {

    private final ReceiptPDFPrinter pdfPrinter;

    public ReceiptPrintController() {
        this.pdfPrinter = new ReceiptPDFPrinter();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] arguments = req.getParameterMap().entrySet().stream()
                .flatMap(arg -> Arrays.stream(arg.getValue())
                        .map(value -> arg.getKey() + "-" + value))
                .toArray(String[]::new);

        Map<Product, Integer> products = ProductFactory.getInstance(arguments);
        DiscountCard discountCard = DiscountCardFactory.getInstance(arguments);
        String path = pdfPrinter.print(products, discountCard);

        resp.setContentType("application/pdf");
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        resp.getOutputStream().write(bytes);
    }
}
