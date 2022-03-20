package ru.clevertec.controllers;

import com.google.gson.Gson;
import ru.clevertec.entity.ProductProducer;
import ru.clevertec.repository.impl.ProductProducerRepositoryImpl;
import ru.clevertec.repository.interfaces.ProductProducerRepository;
import ru.clevertec.service.impl.ProductProducerServiceImpl;
import ru.clevertec.service.interfaces.ProductProducerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet(name = "GetProductProducerByIdController", value = "/product-producer/get-by-id")
public class GetProductProducerByIdController extends HttpServlet {

    private final ProductProducerRepository productProducerRepository;
    private final ProductProducerService productProducerService;

    public GetProductProducerByIdController() {
        this.productProducerRepository = new ProductProducerRepositoryImpl();
        this.productProducerService = new ProductProducerServiceImpl(productProducerRepository);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<ProductProducer> productProducer = productProducerService.findById(request.getParameter("id"));
        if (productProducer.isPresent()){
            try (PrintWriter writer = response.getWriter()) {
                String json = new Gson().toJson(productProducer);
                writer.write(json);
                response.setStatus(200);
            }
        }else {
            response.sendError(400, "Product producer not found.");
        }
    }

}
