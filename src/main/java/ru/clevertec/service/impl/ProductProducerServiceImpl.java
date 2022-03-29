package ru.clevertec.service.impl;

import lombok.RequiredArgsConstructor;
import ru.clevertec.constants.ApplicationConstants;
import ru.clevertec.ecxeption.RepositoryException;
import ru.clevertec.ecxeption.ServiceException;
import ru.clevertec.entity.ProductProducer;
import ru.clevertec.repository.interfaces.ProductProducerRepository;
import ru.clevertec.service.interfaces.ProductProducerService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class ProductProducerServiceImpl implements ProductProducerService {

    private final ProductProducerRepository productProducerRepository;

    @Override
    public List<ProductProducer> getAll() {
        return productProducerRepository.getAll();
    }

    @Override
    public Optional<ProductProducer> findById(String idProducer) {
        Long id = Long.parseLong(idProducer);
        return productProducerRepository.findById(id);
    }

    @Override
    public Optional<ProductProducer> addProducer(Map<String, String> params) {
        ProductProducer productProducer = getProductProducerFromParams(params);
        return productProducerRepository.addProductProducer(productProducer);

    }

    @Override
    public boolean updateProducer(Map<String, String> params) throws ServiceException {
        ProductProducer productProducer = getProductProducerFromParams(params);
        try {
            return productProducerRepository.updateProductProducer(productProducer.getId(),
                    productProducer);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean removeProducer(String idProducer) throws ServiceException {
        Long id = Long.parseLong(idProducer);
        try {
            return productProducerRepository.removeProductProducer(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    private ProductProducer getProductProducerFromParams(Map<String, String> params) {
        ProductProducer productProducer = new ProductProducer();
        if (params.containsKey(ApplicationConstants.PRODUCT_PRODUCER_ID)) {
            Long id = Long.parseLong(params.get(ApplicationConstants.PRODUCT_PRODUCER_ID));
            productProducer.setId(id);
        }


        String name = params.get(ApplicationConstants.PRODUCT_PRODUCER_NAME);
        productProducer.setName(name);
        return productProducer;
    }
}
