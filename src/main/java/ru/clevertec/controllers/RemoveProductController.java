package ru.clevertec.controllers;

import com.google.gson.Gson;
import ru.clevertec.repository.ProductRepository;
import ru.clevertec.repository.impl.ProductRepositoryImpl;
import ru.clevertec.service.interfaces.ProductService;
import ru.clevertec.service.impl.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
        boolean isRemove = productService.removeProduct(productId);
        if (isRemove){
            try (PrintWriter writer = resp.getWriter()) {
                String result = new Gson().toJson(true);
                writer.write(result);
                resp.setStatus(200);
            }
        }else {
            resp.sendError(400, "Product hasn't removed.");
        }
    }
}
