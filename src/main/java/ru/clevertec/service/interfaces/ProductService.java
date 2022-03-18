package ru.clevertec.service.interfaces;

import ru.clevertec.entity.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductService {

    List<Product> getAll();

    Product addProduct(Map<String, String> params);

    Optional<Product> findById(String idProduct);

    boolean updateProduct(Map<String, String> params);

    boolean removeProduct(Long idProduct);
}
