package ru.clevertec.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.service.interfaces.ProductService;

@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

}
