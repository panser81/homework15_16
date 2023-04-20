package com.gmail.spanteleyko.service.model;

import lombok.Data;

@Data
public class ItemDTO {
    private long id;
    private String name;
    private String description;
    private Double price;
}
