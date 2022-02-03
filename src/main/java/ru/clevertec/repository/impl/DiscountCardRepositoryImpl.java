package ru.clevertec.repository.impl;

import ru.clevertec.db.ConnectionManager;
import ru.clevertec.entity.DiscountCard;
import ru.clevertec.entity.Product;
import ru.clevertec.repository.DiscountCardRepository;
import ru.clevertec.sql.SqlRequests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DiscountCardRepositoryImpl implements DiscountCardRepository {

    private static final DiscountCardRepositoryImpl DISCOUNT_CARD_REPOSITORY = new DiscountCardRepositoryImpl();

    private DiscountCardRepositoryImpl() {
    }

    @Override
    public DiscountCard findById(Long idDiscountCard) {
        DiscountCard discountCard = null;
        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement ps = cn.prepareStatement(SqlRequests.GET_DISCOUNT_CARD_BY_ID)) {

            ps.setLong(1, idDiscountCard);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                double discount = rs.getDouble("discount");

                discountCard = new DiscountCard(idDiscountCard, discount);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return discountCard;
    }

    public static DiscountCardRepositoryImpl getInstance() {
        return DISCOUNT_CARD_REPOSITORY;
    }
}
