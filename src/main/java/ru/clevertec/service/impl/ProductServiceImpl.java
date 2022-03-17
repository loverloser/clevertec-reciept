package ru.clevertec.service.impl;

import lombok.RequiredArgsConstructor;
import ru.clevertec.annotation.Cached;
import ru.clevertec.constants.ApplicationConstants;
import ru.clevertec.entity.Producer;
import ru.clevertec.entity.Product;
import ru.clevertec.repository.ProductRepository;
import ru.clevertec.service.interfaces.ProductService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;


    @Override
    public List<Product> getAll() {
        return productRepository.getAll();
    }

    @Override
    @Cached
    public Product addProduct(Map<String, String> params) {
        Product product = getProductFromParams(params);
        return productRepository.addProduct(product);
    }

    @Override
    @Cached
    public Optional<Product> getProduct(Long idProduct) {
        return productRepository.findById(idProduct);
    }

    @Override
    @Cached
    public boolean updateProduct(Map<String, String> params) {
        Product product = getProductFromParams(params);
        return productRepository.updateProduct(product.getId(), product);
    }

    @Override
    @Cached
    public boolean removeProduct(Long idProduct) {
        return productRepository.removeProduct(idProduct);
    }

    private Product getProductFromParams(Map<String, String> params){
        String name = params.get(ApplicationConstants.PRODUCT_NAME_LABEL);
        double price = Double.parseDouble(params.get(ApplicationConstants.PRODUCT_PRICE_LABEL));
        long producerId = Long.parseLong(params.get(ApplicationConstants.PRODUCT_PRODUCER_ID_LABEL));
        return new Product(null, name, price, new Producer(producerId));
    }
}
