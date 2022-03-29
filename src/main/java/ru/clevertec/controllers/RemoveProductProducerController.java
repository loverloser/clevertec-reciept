package ru.clevertec.controllers;

import com.google.gson.Gson;
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

@WebServlet(name = "RemoveProductProducerController", value = "/product-producer/remove")
public class RemoveProductProducerController extends HttpServlet {

    private final ProductProducerRepository productProducerRepository;
    private final ProductProducerService productProducerService;

    public RemoveProductProducerController() {
        this.productProducerRepository = new ProductProducerRepositoryImpl();
        this.productProducerService = new ProductProducerServiceImpl(productProducerRepository);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean isRemove = false;
        try {
            isRemove = productProducerService.removeProducer(req.getParameter("id"));
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        if (isRemove){
            try (PrintWriter writer = resp.getWriter()) {
                String json = new Gson().toJson(true);
                writer.write(json);
                resp.setStatus(200);
            }
        }else {
            resp.sendError(400, "Product producer not found.");
        }

    }
}
