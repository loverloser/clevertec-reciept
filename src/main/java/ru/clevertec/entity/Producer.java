package ru.clevertec.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Producer {
    private Long id;
    private String name;

    public Producer(Long id) {
        this.id = id;
    }
}
