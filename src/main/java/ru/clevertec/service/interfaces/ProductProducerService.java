package ru.clevertec.service.interfaces;

import ru.clevertec.ecxeption.ServiceException;
import ru.clevertec.entity.ProductProducer;

import java.util.List;
import java.util.Optional;

public interface ProductProducerService {

    List<ProductProducer> getAll();

    Optional<ProductProducer> addProducer(ProductProducer productProducer);

    Optional<ProductProducer> findById(Long idProducer);

    boolean updateProducer(Long id, ProductProducer productProducer) throws ServiceException;

    boolean removeProducer(Long idProducer) throws ServiceException;
}
