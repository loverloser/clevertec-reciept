package ru.clevertec.controllers;

import com.google.gson.Gson;
import ru.clevertec.constants.ApplicationConstants;
import ru.clevertec.entity.DiscountCard;
import ru.clevertec.repository.impl.DiscountCardRepositoryImpl;
import ru.clevertec.repository.interfaces.DiscountCardRepository;
import ru.clevertec.service.impl.DiscountCardServiceImpl;
import ru.clevertec.service.interfaces.DiscountCardService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@WebServlet(name = "AddDiscountCardController", value = "/discount-card/add")
public class AddDiscountCardController extends HttpServlet {

    private final DiscountCardRepository discountCardRepository;
    private final DiscountCardService discountCardService;

    public AddDiscountCardController() {
        discountCardRepository = new DiscountCardRepositoryImpl();
        discountCardService = new DiscountCardServiceImpl(discountCardRepository);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> params = new HashMap<>();
        DiscountCard fromJson;
        try (BufferedReader reader = request.getReader()) {
            fromJson = new Gson().fromJson(reader, DiscountCard.class);
        }
        params.put(ApplicationConstants.DISCOUNT_CARD_DISCOUNT, String.valueOf(fromJson.getDiscount()));
        Optional<DiscountCard> discountCard = discountCardService.addDiscountCard(params);
        try (PrintWriter writer = response.getWriter()) {
            if (discountCard.isPresent()){
                String json = new Gson().toJson(discountCard);
                writer.write(json);
                response.setStatus(200);
            }else {
                response.sendError(400, "Cannot add discount card.");
            }
        }

    }
}
