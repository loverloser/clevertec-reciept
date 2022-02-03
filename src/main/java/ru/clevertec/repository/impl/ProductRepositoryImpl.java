package ru.clevertec.repository.impl;

import ru.clevertec.db.ConnectionManager;
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

public class ProductRepositoryImpl implements ProductRepository {

    private static final ProductRepositoryImpl PRODUCT_REPOSITORY = new ProductRepositoryImpl();

    private ProductRepositoryImpl() {
    }

    public static ProductRepositoryImpl getProductRepository() {
        return PRODUCT_REPOSITORY;
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement ps = cn.prepareStatement(SqlRequests.GET_ALL_PRODUCTS)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");

                Product product = new Product(id, name, price);
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
    public Product getProduct(Long idProduct) {
        Product product = null;
        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement ps = cn.prepareStatement(SqlRequests.GET_PRODUCT_BY_ID)) {

            ps.setLong(1, idProduct);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");

                product = new Product(idProduct, name, price);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return product;
    }

    @Override
    public Long updateProduct(Product product) {
        Long key = null;

        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement ps = cn.prepareStatement(SqlRequests.UPDATE_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setLong(3, product.getId());

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
    public Long removeProduct(Long idProduct) {
        Long key = null;

        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement ps = cn.prepareStatement(SqlRequests.REMOVE_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, idProduct);

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
}
