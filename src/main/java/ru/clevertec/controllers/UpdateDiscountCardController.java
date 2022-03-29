package ru.clevertec.controllers;

import com.google.gson.Gson;
import ru.clevertec.constants.ApplicationConstants;
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
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "UpdateDiscountCardController", value = "/discount-card/update")
public class UpdateDiscountCardController extends HttpServlet {

    private final DiscountCardRepository discountCardRepository;
    private final DiscountCardService discountCardService;

    public UpdateDiscountCardController() {
        discountCardRepository = new DiscountCardRepositoryImpl();
        discountCardService = new DiscountCardServiceImpl(discountCardRepository);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean isUpdate = false;
        Map<String, String> params = new HashMap<>();
        params.put(ApplicationConstants.DISCOUNT_CARD_ID, request.getParameter("id"));
        params.put(ApplicationConstants.DISCOUNT_CARD_DISCOUNT, request.getParameter("discount"));

        try {
            isUpdate = discountCardService.updateDiscountCard(params);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        if (isUpdate){
            try (PrintWriter writer = response.getWriter()) {
                String json = new Gson().toJson(true);
                writer.write(json);
                response.setStatus(200);
            }
        }else {
            response.sendError(400, "Cannot update discount card.");
        }
    }
}
