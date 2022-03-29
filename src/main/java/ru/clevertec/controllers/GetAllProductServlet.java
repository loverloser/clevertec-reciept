package ru.clevertec.controllers;

import com.google.gson.Gson;
import ru.clevertec.entity.Product;
import ru.clevertec.repository.interfaces.ProductRepository;
import ru.clevertec.repository.impl.ProductRepositoryImpl;
import ru.clevertec.service.interfaces.ProductService;
import ru.clevertec.service.impl.ProductServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "GetAllProductsServlet", value = "/product/get-all")
public class GetAllProductServlet extends HttpServlet {

    private final ProductRepository productRepository;
    private final ProductService productService;

    public GetAllProductServlet() {
        this.productRepository = new ProductRepositoryImpl();
        this.productService = new ProductServiceImpl(productRepository);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> all = productService.getAll();
        String products = new Gson().toJson(all);
        try (PrintWriter writer = response.getWriter()) {
            writer.write(products);
        }

        response.setStatus(200);
    }

}
