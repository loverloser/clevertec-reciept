package ru.clevertec.repository.interfaces;

import ru.clevertec.ecxeption.RepositoryException;
import ru.clevertec.entity.ProductProducer;

import java.util.List;
import java.util.Optional;

public interface ProductProducerRepository {

    List<ProductProducer> getAll();

    Optional<ProductProducer> findById(Long idProduct);

    Optional<ProductProducer> addProductProducer(ProductProducer productProducer);

    boolean updateProductProducer(Long id, ProductProducer productProducer) throws RepositoryException;

    boolean removeProductProducer(Long idProductProducer) throws RepositoryException;
}
