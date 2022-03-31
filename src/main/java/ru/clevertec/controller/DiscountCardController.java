package ru.clevertec.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecxeption.DiscountCardNotFoundException;
import ru.clevertec.entity.DiscountCard;
import ru.clevertec.service.interfaces.DiscountCardService;

@RequiredArgsConstructor
@RequestMapping("/cards")
@RestController
public class DiscountCardController {

    private final DiscountCardService discountCardService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DiscountCard findById(@PathVariable String id){
        return discountCardService.findById(id)
                .orElseThrow(DiscountCardNotFoundException::new);
    }

}
