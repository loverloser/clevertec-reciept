package ru.clevertec.service.impl;

import lombok.RequiredArgsConstructor;
import ru.clevertec.annotation.Cached;
import ru.clevertec.entity.Product;
import ru.clevertec.repository.impl.ProductRepositoryImpl;
import ru.clevertec.service.ProductService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepositoryImpl productRepository;

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
    public Optional<Product> getProduct(Long idProduct) {
        return productRepository.findById(idProduct);
    }

    @Override
    @Cached
    public boolean updateProduct(Product product) {
        return productRepository.updateProduct(product);
    }

    @Override
    @Cached
    public boolean removeProduct(Long idProduct) {
        return productRepository.removeProduct(idProduct);
    }
}
