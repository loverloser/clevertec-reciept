package ru.clevertec.controllers;

import com.google.gson.Gson;
import ru.clevertec.constants.ApplicationConstants;
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

@WebServlet(name = "UpdateProductController", value = "/product/update")
public class UpdateProductController extends HttpServlet {

    private final ProductRepository productRepository;
    private final ProductService productService;

    public UpdateProductController() {
        this.productRepository = new ProductRepositoryImpl();
        this.productService = new ProductServiceImpl(productRepository);
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> map = new HashMap<>();
        map.put(ApplicationConstants.PRODUCT_ID, req.getParameter("product_id"));
        map.put(ApplicationConstants.PRODUCT_PRODUCER_ID, req.getParameter("product_producer_id"));
        map.put(ApplicationConstants.PRODUCT_PRICE, req.getParameter("product_price"));
        map.put(ApplicationConstants.PRODUCT_NAME, req.getParameter("product_name"));

        boolean isAdd = productService.updateProduct(map);
        if (isAdd) {
            try (PrintWriter writer = resp.getWriter()) {
                String result = new Gson().toJson(true);
                writer.write(result);
                resp.setStatus(200);
            }
        }else {
            resp.sendError(400, "Product hasn't updated.");
        }

    }
}
