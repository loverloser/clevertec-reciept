package ru.clevertec.service.interfaces;

import ru.clevertec.ecxeption.ServiceException;
import ru.clevertec.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> getAll();

    Optional<Product> addProduct(Product product);

    Optional<Product> findById(Long idProduct);

    boolean updateProduct(Long id, Product product) throws ServiceException;

    boolean removeProduct(Long idProduct) throws ServiceException;
}
