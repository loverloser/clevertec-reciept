package ru.clevertec.controllers;

import com.google.gson.Gson;
import ru.clevertec.constants.ApplicationConstants;
import ru.clevertec.entity.Product;
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
import java.util.Objects;

@WebServlet(name = "AddProductController", value = "/product/add")
public class AddProductController extends HttpServlet {

    private final ProductRepository productRepository;
    private final ProductService productService;

    public AddProductController() {
        this.productRepository = new ProductRepositoryImpl();
        this.productService = new ProductServiceImpl(productRepository);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> map = new HashMap<>();
        map.put(ApplicationConstants.PRODUCT_NAME_LABEL, req.getParameter("product_name"));
        map.put(ApplicationConstants.PRODUCT_PRICE_LABEL, req.getParameter("product_price"));
        map.put(ApplicationConstants.PRODUCT_PRODUCER_ID_LABEL, req.getParameter("product_producer_id"));
        Product product = productService.addProduct(map);
        if (Objects.nonNull(product)) {
            try (PrintWriter writer = resp.getWriter()) {
                String id = new Gson().toJson(product);
                writer.write(id);
                resp.setStatus(200);
            }
        }else {
            resp.sendError(400, "Product hasn't added.");
        }
    }
}
