package ru.clevertec.controllers;

import com.google.gson.Gson;
import ru.clevertec.entity.DiscountCard;
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
import java.util.List;

@WebServlet(name = "GetAllDiscountCardsController", value = "/discount-card/get-all")
public class GetAllDiscountCardsController extends HttpServlet {

    private final DiscountCardRepository discountCardRepository;
    private final DiscountCardService discountCardService;

    public GetAllDiscountCardsController() {
        discountCardRepository = new DiscountCardRepositoryImpl();
        discountCardService = new DiscountCardServiceImpl(discountCardRepository);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<DiscountCard> discountCards = discountCardService.getAll();
        try (PrintWriter writer = response.getWriter()) {
            String json = new Gson().toJson(discountCards);
            writer.write(json);
        }

        response.setStatus(200);
    }

}
