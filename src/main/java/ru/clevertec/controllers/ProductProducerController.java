package ru.clevertec.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.service.interfaces.ProductProducerService;

@RequiredArgsConstructor
@RestController
public class ProductProducerController {

    private final ProductProducerService productProducerService;

}
