package ru.clevertec.repository.interfaces;

import ru.clevertec.ecxeption.RepositoryException;
import ru.clevertec.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> getAll();

    Optional<Product> findById(Long idProduct);

    Product addProduct(Product product);

    boolean updateProduct(Long id, Product product) throws RepositoryException;

    boolean removeProduct(Long idProduct) throws RepositoryException;
}
