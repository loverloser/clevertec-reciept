package ru.clevertec.controllers;

import com.google.gson.Gson;
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
import java.util.Optional;

@WebServlet(name = "GetProductByIdController", value = "/product/get-by-id")
public class GetProductByIdController extends HttpServlet {

    private final ProductRepository productRepository;
    private final ProductService productService;

    public GetProductByIdController() {
        this.productRepository = new ProductRepositoryImpl();
        this.productService = new ProductServiceImpl(productRepository);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getParameter("product_id");
        Optional<Product> product = productService.getProduct(productId);
        if (product.isPresent()){
            try (PrintWriter writer = response.getWriter()) {
                Product result = product.get();
                String json = new Gson().toJson(result);
                writer.write(json);
                response.setStatus(200);
            }
        }else {
            response.sendError(400, "Product not found.");
        }
    }

}
