package ru.clevertec.service.impl;

import ru.clevertec.annotation.Cached;
import ru.clevertec.entity.Product;
import ru.clevertec.repository.impl.ProductRepositoryImpl;
import ru.clevertec.service.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    private final ProductRepositoryImpl productRepository = ProductRepositoryImpl.getProductRepository();

    @Override
    public List<Product> getAll() {
        return productRepository.getAll();
    }

    @Override
    @Cached
    public Long addProduct(Product product) {
        return productRepository.addProduct(product);
    }

    @Override
    @Cached
    public Product getProduct(Long idProduct) {
        return productRepository.getProduct(idProduct);
    }

    @Override
    @Cached
    public Long updateProduct(Product product) {
        return productRepository.updateProduct(product);
    }

    @Override
    @Cached
    public Long removeProduct(Long idProduct) {
        return productRepository.removeProduct(idProduct);
    }
}
