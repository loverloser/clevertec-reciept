package ru.clevertec.repository.impl;

import org.springframework.stereotype.Repository;
import ru.clevertec.db.ConnectionManager;
import ru.clevertec.ecxeption.DiscountCardNotFoundException;
import ru.clevertec.ecxeption.RepositoryException;
import ru.clevertec.entity.DiscountCard;
import ru.clevertec.repository.interfaces.DiscountCardRepository;
import ru.clevertec.sql.SqlRequests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DiscountCardRepositoryImpl implements DiscountCardRepository {

    @Override
    public List<DiscountCard> getAll() {
        List<DiscountCard> discountCards = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(SqlRequests.GET_ALL_DISCOUNT_CARDS)) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                double discount = resultSet.getDouble("discount");
                discountCards.add(new DiscountCard(id, discount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return discountCards;
    }

    @Override
    public Optional<DiscountCard> findById(Long idDiscountCard) {
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

        return Optional.ofNullable(discountCard);
    }

    @Override
    public Optional<DiscountCard> addDiscountCard(DiscountCard discountCard) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(SqlRequests.ADD_DISCOUNT_CARD,
                     Statement.RETURN_GENERATED_KEYS)) {

            ps.setDouble(1, discountCard.getDiscount());

            if (ps.executeUpdate() == 1) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    discountCard.setId(generatedKeys.getLong(1));
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.ofNullable(discountCard);
    }

    @Override
    public boolean updateDiscountCard(Long idDiscountCard, DiscountCard discountCard) throws RepositoryException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(SqlRequests.UPDATE_DISCOUNT_CARD)) {

            ps.setDouble(1, discountCard.getDiscount());
            ps.setDouble(2, idDiscountCard);

            if (ps.executeUpdate() == 1) {
                return true;
            }else {
                throw new DiscountCardNotFoundException();
            }

        } catch (SQLException | DiscountCardNotFoundException throwables) {
            throw new RepositoryException(throwables);
        }
    }

    @Override
    public boolean removeDiscountCard(Long idDiscountCard) throws RepositoryException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(SqlRequests.REMOVE_DISCOUNT_CARD)) {

            ps.setLong(1, idDiscountCard);

            if (ps.executeUpdate() == 1) {
                return true;
            }else {
                throw new DiscountCardNotFoundException();
            }

        } catch (SQLException | DiscountCardNotFoundException throwables) {
            throw new RepositoryException(throwables);
        }
    }

}
