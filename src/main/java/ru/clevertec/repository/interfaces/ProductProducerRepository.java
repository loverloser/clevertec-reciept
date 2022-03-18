package ru.clevertec.repository.interfaces;

import ru.clevertec.entity.ProductProducer;

import java.util.List;
import java.util.Optional;

public interface ProductProducerRepository {

    List<ProductProducer> getAll();

    ProductProducer addProductProducer(ProductProducer productProducer);

    Optional<ProductProducer> findById(Long idProduct);

    boolean updateProductProducer(Long id, ProductProducer productProducer);

    boolean removeProductProducer(Long idProductProducer);
}
