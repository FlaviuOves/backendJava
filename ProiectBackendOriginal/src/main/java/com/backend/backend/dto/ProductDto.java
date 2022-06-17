package com.backend.backend.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class ProductDto implements Serializable {
    private String name;
    private String description;
    private Double price;
}
