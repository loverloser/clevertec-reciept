package ru.clevertec.repository.impl;

import org.springframework.stereotype.Repository;
import ru.clevertec.db.ConnectionManager;
import ru.clevertec.ecxeption.ProductNotFoundException;
import ru.clevertec.ecxeption.RepositoryException;
import ru.clevertec.entity.Product;
import ru.clevertec.entity.ProductProducer;
import ru.clevertec.repository.interfaces.ProductRepository;
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
                ProductProducer productProducer = new ProductProducer(producerId, producerName);

                Long productId = rs.getLong("id");
                String productName = rs.getString("name");
                double productPrice = rs.getDouble("price");

                Product product = new Product(productId, productName, productPrice, productProducer);
                products.add(product);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return products;
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
                ProductProducer productProducer = new ProductProducer(producerId, producerName);

                String name = rs.getString("name");
                double price = rs.getDouble("price");

                product = new Product(idProduct, name, price, productProducer);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.ofNullable(product);
    }

    @Override
    public Optional<Product> addProduct(Product product) {
        Product result = null;

        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement ps = cn.prepareStatement(SqlRequests.ADD_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setLong(3, product.getProductProducer().getId());

            int isUpdate = ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (isUpdate == 1) {
                if (generatedKeys.next()) {
                    product.setId(generatedKeys.getLong(1));
                }
                Optional<Product> maybeProduct = findById(product.getId());
                if (maybeProduct.isPresent()) {
                    result = maybeProduct.get();
                }
            }
            //TODO refactor this add code

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.ofNullable(result);
    }

    @Override
    public boolean updateProduct(Long id, Product product) throws RepositoryException {
        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement ps = cn.prepareStatement(SqlRequests.UPDATE_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setLong(3, product.getProductProducer().getId());
            ps.setLong(4, id);

            if (ps.executeUpdate() == 1) {
                return true;
            } else {
                throw new ProductNotFoundException();
            }
        } catch (SQLException | ProductNotFoundException throwables) {
            throw new RepositoryException(throwables);
        }
    }

    @Override
    public boolean removeProduct(Long idProduct) throws RepositoryException {
        try (Connection cn = ConnectionManager.getConnection();
             PreparedStatement ps = cn.prepareStatement(SqlRequests.REMOVE_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, idProduct);

            if (ps.executeUpdate() == 1) {
                return true;
            } else {
                throw new ProductNotFoundException();
            }
        } catch (SQLException | ProductNotFoundException throwables) {
            throw new RepositoryException(throwables);
        }
    }

}
