package ru.clevertec.service.interfaces;

import ru.clevertec.ecxeption.ServiceException;
import ru.clevertec.entity.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductService {

    List<Product> getAll();

    Optional<Product> addProduct(Map<String, String> params);

    Optional<Product> findById(String idProduct);

    boolean updateProduct(Map<String, String> params) throws ServiceException;

    boolean removeProduct(Long idProduct) throws ServiceException;
}
