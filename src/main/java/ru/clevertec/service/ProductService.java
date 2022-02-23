package ru.clevertec.service;

import ru.clevertec.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> getAll();

    Long addProduct(Product product);

    Optional<Product> getProduct(Long idProduct);

    boolean updateProduct(Product product);

    boolean removeProduct(Long idProduct);
}
