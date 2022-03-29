package ru.clevertec.controllers;

import com.google.gson.Gson;
import ru.clevertec.entity.ProductProducer;
import ru.clevertec.repository.impl.ProductProducerRepositoryImpl;
import ru.clevertec.repository.interfaces.ProductProducerRepository;
import ru.clevertec.service.impl.ProductProducerServiceImpl;
import ru.clevertec.service.interfaces.ProductProducerService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "GetAllProductProducersController", value = "/product-producer/get-all")
public class GetAllProductProducersController extends HttpServlet {

    private final ProductProducerRepository productProducerRepository;
    private final ProductProducerService productProducerService;

    public GetAllProductProducersController() {
        this.productProducerRepository = new ProductProducerRepositoryImpl();
        this.productProducerService = new ProductProducerServiceImpl(productProducerRepository);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ProductProducer> productProducers = productProducerService.getAll();
        try (PrintWriter writer = response.getWriter()) {
            String json = new Gson().toJson(productProducers);
            writer.write(json);
        }

        response.setStatus(200);
    }

}
