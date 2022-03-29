package ru.clevertec.controllers;

import com.google.gson.Gson;
import lombok.extern.java.Log;
import ru.clevertec.ecxeption.ServiceException;
import ru.clevertec.repository.impl.ProductRepositoryImpl;
import ru.clevertec.repository.interfaces.ProductRepository;
import ru.clevertec.service.impl.ProductServiceImpl;
import ru.clevertec.service.interfaces.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Log
@WebServlet(name = "RemoveProductController", value = "/product/remove")
public class RemoveProductController extends HttpServlet {

    private final ProductRepository productRepository;
    private final ProductService productService;

    public RemoveProductController() {
        this.productRepository = new ProductRepositoryImpl();
        this.productService = new ProductServiceImpl(productRepository);
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long productId = Long.parseLong(req.getParameter("product_id"));
        boolean isRemove = false;
        try {
            isRemove = productService.removeProduct(productId);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        try (PrintWriter writer = resp.getWriter()) {
            if (isRemove) {
                String result = new Gson().toJson(true);
                writer.write(result);
                resp.setStatus(200);
            } else {
                resp.sendError(400, "Product not found.");
            }
        }
    }
}
