package ru.clevertec.service.interfaces;

import ru.clevertec.entity.ProductProducer;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductProducerService {

    List<ProductProducer> getAll();

    ProductProducer addProducer(Map<String, String> params);

    Optional<ProductProducer> findById(String idProducer);

    boolean updateProducer(Map<String, String> params);

    boolean removeProducer(String idProducer);
}
