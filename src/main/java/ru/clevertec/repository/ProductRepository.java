package ru.clevertec.repository;

import ru.clevertec.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> getAll();

    Long addProduct(Product product);

    Optional<Product> findById(Long idProduct);

    boolean updateProduct(Product product);

    boolean removeProduct(Long idProduct);
}
