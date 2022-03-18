package ru.clevertec.service.impl;

import lombok.RequiredArgsConstructor;
import ru.clevertec.constants.ApplicationConstants;
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
    public ProductProducer addProducer(Map<String, String> params) {
        ProductProducer productProducer = getProductProducerFromParams(params);
        return productProducerRepository.addProductProducer(productProducer);
    }

    @Override
    public boolean updateProducer(Map<String, String> params) {
        ProductProducer productProducer = getProductProducerFromParams(params);
        return productProducerRepository.updateProductProducer(productProducer.getId(),
                productProducer);
    }

    @Override
    public boolean removeProducer(String idProducer) {
        Long id = Long.parseLong(idProducer);
        return productProducerRepository.removeProductProducer(id);
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
