package ru.clevertec.repository.impl;

import ru.clevertec.entity.ProductProducer;
import ru.clevertec.repository.interfaces.ProductProducerRepository;

import java.util.List;
import java.util.Optional;

public class ProductProducerRepositoryImpl implements ProductProducerRepository {
    
    @Override
    public List<ProductProducer> getAll() {
        return null;
    }

    @Override
    public ProductProducer addProductProducer(ProductProducer productProducer) {
        return null;
    }

    @Override
    public Optional<ProductProducer> findById(Long idProductProducer) {
        return Optional.empty();
    }

    @Override
    public boolean updateProductProducer(Long id, ProductProducer productProducer) {
        return false;
    }

    @Override
    public boolean removeProductProducer(Long idProductProducer) {
        return false;
    }
}
