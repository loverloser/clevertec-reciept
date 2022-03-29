package ru.clevertec.controllers;

import com.google.gson.Gson;
import ru.clevertec.ecxeption.ServiceException;
import ru.clevertec.repository.impl.DiscountCardRepositoryImpl;
import ru.clevertec.repository.interfaces.DiscountCardRepository;
import ru.clevertec.service.impl.DiscountCardServiceImpl;
import ru.clevertec.service.interfaces.DiscountCardService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "RemoveDiscountCardController", value = "/discount-card/remove")
public class RemoveDiscountCardController extends HttpServlet {

    private final DiscountCardRepository discountCardRepository;
    private final DiscountCardService discountCardService;

    public RemoveDiscountCardController() {
        discountCardRepository = new DiscountCardRepositoryImpl();
        discountCardService = new DiscountCardServiceImpl(discountCardRepository);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean isRemove = false;
        try {
            isRemove = discountCardService.removeDiscountCard(request.getParameter("id"));
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        if (isRemove){
            try (PrintWriter writer = response.getWriter()) {
                String json = new Gson().toJson(true);
                writer.write(json);
            }
        }else {
            response.sendError(400, "Discount card be remove.");
        }
    }
}
