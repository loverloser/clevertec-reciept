package ru.clevertec.repository;

import ru.clevertec.entity.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> getAll();

    Long addProduct(Product product);

    Product getProduct(Long idProduct);

    Long updateProduct(Product product);

    Long removeProduct(Long idProduct);
}
