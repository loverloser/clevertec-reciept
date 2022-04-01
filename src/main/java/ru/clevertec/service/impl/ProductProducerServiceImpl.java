package ru.clevertec.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.ecxeption.RepositoryException;
import ru.clevertec.ecxeption.ServiceException;
import ru.clevertec.entity.ProductProducer;
import ru.clevertec.repository.interfaces.ProductProducerRepository;
import ru.clevertec.service.interfaces.ProductProducerService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductProducerServiceImpl implements ProductProducerService {

    private final ProductProducerRepository productProducerRepository;

    @Override
    public List<ProductProducer> getAll() {
        return productProducerRepository.getAll();
    }

    @Override
    public Optional<ProductProducer> findById(Long idProducer) {
        return productProducerRepository.findById(idProducer);
    }

    @Override
    public Optional<ProductProducer> addProducer(ProductProducer productProducer) {
        return productProducerRepository.addProductProducer(productProducer);

    }

    @Override
    public boolean updateProducer(Long id, ProductProducer productProducer) throws ServiceException {
        try {
            return productProducerRepository.updateProductProducer(id, productProducer);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean removeProducer(Long idProducer) throws ServiceException {
        try {
            return productProducerRepository.removeProductProducer(idProducer);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}
