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
import ru.clevertec.entity.DiscountCard;
import ru.clevertec.service.interfaces.DiscountCardService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/cards")
@RestController
public class DiscountCardController {

    private final DiscountCardService discountCardService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DiscountCard findById(@PathVariable Long id){
        return discountCardService.findById(id)
                .orElseThrow(DiscountCardNotFoundException::new);
    }

    @GetMapping
    public List<DiscountCard> findAll(){
        return discountCardService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DiscountCard save(@RequestBody DiscountCard discountCard){
        return discountCardService.addDiscountCard(discountCard)
                .orElseThrow(DiscountCardNotFoundException::new);
    }

    @SneakyThrows
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean update(@RequestBody DiscountCard discountCard, @PathVariable Long id){
        return discountCardService.updateDiscountCard(id, discountCard);
    }

    @SneakyThrows
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean remove(@PathVariable Long id){
        return discountCardService.removeDiscountCard(id);
    }

}
