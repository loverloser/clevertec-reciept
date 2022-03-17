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
import java.util.Optional;

@WebServlet(name = "GetDiscountCardByIdController", value = "/discount-card/get-by-id")
public class GetDiscountCardByIdController extends HttpServlet {

    private final DiscountCardRepository discountCardRepository;
    private final DiscountCardService discountCardService;

    public GetDiscountCardByIdController() {
        this.discountCardRepository = new DiscountCardRepositoryImpl();
        this.discountCardService = new DiscountCardServiceImpl(discountCardRepository);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String discountCardId = request.getParameter("discount_card_id");
        Optional<DiscountCard> discountCard = discountCardService.findById(discountCardId);
        if (discountCard.isPresent()){
            try (PrintWriter writer = response.getWriter()) {
                DiscountCard result = discountCard.get();
                String json = new Gson().toJson(result);
                writer.write(json);
                response.setStatus(200);
            }
        }else {
            response.sendError(400, "Discount card not found.");
        }

    }

}
