package ru.clevertec.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecxeption.DiscountCardNotFoundException;
import ru.clevertec.entity.ProductProducer;
import ru.clevertec.service.interfaces.ProductProducerService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping(value = "/product-producers")
@RestController
public class ProductProducerController {

    private final ProductProducerService productProducerService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductProducer findById(@PathVariable Long id){
        return productProducerService.findById(id)
                .orElseThrow(DiscountCardNotFoundException::new);
    }

    @GetMapping
    public List<ProductProducer> findAll(){
        return productProducerService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductProducer save(@RequestBody ProductProducer productProducer){
        return productProducerService.addProducer(productProducer)
                .orElseThrow(DiscountCardNotFoundException::new);
    }

    @SneakyThrows
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean update(@RequestBody ProductProducer productProducer, @PathVariable Long id){
        return productProducerService.updateProducer(id, productProducer);
    }

    @SneakyThrows
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean remove(@PathVariable Long id){
        return productProducerService.removeProducer(id);
    }
}
