package ru.clevertec.controllers;

import com.google.gson.Gson;
import lombok.extern.java.Log;
import ru.clevertec.constants.ApplicationConstants;
import ru.clevertec.ecxeption.ServiceException;
import ru.clevertec.repository.interfaces.ProductRepository;
import ru.clevertec.repository.impl.ProductRepositoryImpl;
import ru.clevertec.service.impl.ProductServiceImpl;
import ru.clevertec.service.interfaces.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Log
@WebServlet(name = "UpdateProductController", value = "/product/update")
public class UpdateProductController extends HttpServlet {

    private final ProductRepository productRepository;
    private final ProductService productService;

    public UpdateProductController() {
        this.productRepository = new ProductRepositoryImpl();
        this.productService = new ProductServiceImpl(productRepository);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean isUpdate = false;
        Map<String, String> map = new HashMap<>();
        map.put(ApplicationConstants.PRODUCT_ID, request.getParameter("product_id"));
        map.put(ApplicationConstants.PRODUCT_PRODUCER_ID, request.getParameter("product_producer_id"));
        map.put(ApplicationConstants.PRODUCT_PRICE, request.getParameter("product_price"));
        map.put(ApplicationConstants.PRODUCT_NAME, request.getParameter("product_name"));

        try {
            isUpdate = productService.updateProduct(map);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        try (PrintWriter writer = response.getWriter()) {
            if (isUpdate) {
                String result = new Gson().toJson(true);
                writer.write(result);
            }else {
                response.sendError(400, "Cannot update product.");
            }
        }

    }
}
