package ru.clevertec.controllers;

import com.google.gson.Gson;
import ru.clevertec.constants.ApplicationConstants;
import ru.clevertec.ecxeption.ServiceException;
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
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "UpdateProductProducerController", value = "/product-producer/update")
public class UpdateProductProducerController extends HttpServlet {

    private final ProductProducerRepository productProducerRepository;
    private final ProductProducerService productProducerService;

    public UpdateProductProducerController() {
        this.productProducerRepository = new ProductProducerRepositoryImpl();
        this.productProducerService = new ProductProducerServiceImpl(productProducerRepository);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean isUpdate = false;
        Map<String, String> params = new HashMap<>();
        params.put(ApplicationConstants.PRODUCT_PRODUCER_ID, req.getParameter("id"));
        params.put(ApplicationConstants.PRODUCT_PRODUCER_NAME, req.getParameter("name"));
        try {
            isUpdate = productProducerService.updateProducer(params);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        if (isUpdate) {
            try (PrintWriter writer = resp.getWriter()) {
                String json = new Gson().toJson(true);
                writer.write(json);
            }

        } else {
            resp.sendError(400, "Cannot update product producer.");
        }
    }
}
