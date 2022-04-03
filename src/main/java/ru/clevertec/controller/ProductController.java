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
import ru.clevertec.ecxeption.ProductNotFoundException;
import ru.clevertec.entity.Product;
import ru.clevertec.service.interfaces.ProductService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping(value = "/products")
@RestController
public class ProductController {

    private final ProductService productService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product findById(@PathVariable Long id){
        return productService.findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }

    @GetMapping
    public List<Product> findAll(){
        return productService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product save(@RequestBody Product product){
        return productService.addProduct(product)
                .orElseThrow(ProductNotFoundException::new);
    }

    @SneakyThrows
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean update(@RequestBody Product product, @PathVariable Long id){
        return productService.updateProduct(id, product);
    }

    @SneakyThrows
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean remove(@PathVariable Long id){
        return productService.removeProduct(id);
    }
}
