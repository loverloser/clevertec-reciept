package ru.clevertec.repository.impl;

import ru.clevertec.db.ConnectionManager;
import ru.clevertec.ecxeption.ProductNotFoundException;
import ru.clevertec.entity.Producer;
import ru.clevertec.entity.Product;
import ru.clevertec.repository.ProductRepository;
import ru.clevertec.sql.SqlRequests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement ps = cn.prepareStatement(SqlRequests.GET_ALL_PRODUCTS)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Long producerId = rs.getLong("producer_id");
                String producerName = rs.getString("producer_name");
                Producer producer = new Producer(producerId, producerName);

                Long productId = rs.getLong("id");
                String productName = rs.getString("name");
                double productPrice = rs.getDouble("price");

                Product product = new Product(productId, productName, productPrice, producer);
                products.add(product);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return products;
    }

    @Override
    public Long addProduct(Product product) {
        Long key = null;

        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement ps = cn.prepareStatement(SqlRequests.ADD_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();

            if (keys.next()) {
                key = keys.getLong(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return key;
    }

    @Override
    public Optional<Product> findById(Long idProduct) {
        Product product = null;
        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement ps = cn.prepareStatement(SqlRequests.GET_PRODUCT_BY_ID)) {

            ps.setLong(1, idProduct);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Long producerId = rs.getLong("producer_id");
                String producerName = rs.getString("producer_name");
                Producer producer = new Producer(producerId, producerName);

                String name = rs.getString("name");
                double price = rs.getDouble("price");

                product = new Product(idProduct, name, price, producer);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.ofNullable(product);
    }

    @Override
    public boolean updateProduct(Product product) {
        boolean isUpdated = false;

        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement ps = cn.prepareStatement(SqlRequests.UPDATE_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setLong(3, product.getProducer().getId());
            ps.setLong(4, product.getId());

            isUpdated =  ps.executeUpdate() != 0;


            if (!isUpdated){
                throw new ProductNotFoundException();
            }
        } catch (SQLException | ProductNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return isUpdated;
    }

    @Override
    public boolean removeProduct(Long idProduct) {
        boolean isRemoved = false;
        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement ps = cn.prepareStatement(SqlRequests.REMOVE_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, idProduct);

            isRemoved = ps.executeUpdate() != 0;

            if (!isRemoved) {
                throw new ProductNotFoundException();
            }
        } catch (SQLException | ProductNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return isRemoved;
    }

}
