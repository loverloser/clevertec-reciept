package ru.clevertec.repository.impl;

import ru.clevertec.db.ConnectionManager;
import ru.clevertec.ecxeption.ProductProducerNotFoundException;
import ru.clevertec.ecxeption.RepositoryException;
import ru.clevertec.entity.ProductProducer;
import ru.clevertec.repository.interfaces.ProductProducerRepository;
import ru.clevertec.sql.SqlRequests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductProducerRepositoryImpl implements ProductProducerRepository {

    @Override
    public List<ProductProducer> getAll() {
        List<ProductProducer> productProducers = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(SqlRequests.GET_ALL_PRODUCT_PRODUCERS)) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                productProducers.add(new ProductProducer(id, name));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return productProducers;
    }

    @Override
    public Optional<ProductProducer> findById(Long idProductProducer) {
        ProductProducer productProducer = null;
        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement ps = cn.prepareStatement(SqlRequests.GET_PRODUCT_PRODUCER_BY_ID)) {

            ps.setLong(1, idProductProducer);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                productProducer = new ProductProducer(idProductProducer, name);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.ofNullable(productProducer);
    }

    @Override
    public Optional<ProductProducer> addProductProducer(ProductProducer productProducer) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(SqlRequests.ADD_PRODUCT_PRODUCER,
                     Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, productProducer.getName());

            if (ps.executeUpdate() == 1) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    productProducer.setId(generatedKeys.getLong(1));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.ofNullable(productProducer);
    }

    @Override
    public boolean updateProductProducer(Long id, ProductProducer productProducer) throws RepositoryException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(SqlRequests.UPDATE_PRODUCT_PRODUCER,
                     Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, productProducer.getName());
            ps.setLong(2, id);

            if (ps.executeUpdate() == 1) {
                return true;
            } else {
                throw new ProductProducerNotFoundException();
            }
        } catch (SQLException | ProductProducerNotFoundException throwables) {
            throw new RepositoryException(throwables);
        }
    }

    @Override
    public boolean removeProductProducer(Long idProductProducer) throws RepositoryException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(SqlRequests.REMOVE_PRODUCT_PRODUCER)) {

            ps.setLong(1, idProductProducer);

            if (ps.executeUpdate() == 1) {
                return true;
            } else {
                throw new ProductProducerNotFoundException();
            }

        } catch (SQLException | ProductProducerNotFoundException throwables) {
            throw new RepositoryException(throwables);
        }
    }
}
