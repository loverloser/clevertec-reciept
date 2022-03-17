package ru.clevertec.repository.interfaces;

import ru.clevertec.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> getAll();

    Product addProduct(Product product);

    Optional<Product> findById(Long idProduct);

    boolean updateProduct(Long id, Product product);

    boolean removeProduct(Long idProduct);
}
