package com.theverygroup.products.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    private String id;
    private String name;
    private String description;
    private Price price;
    private Type type;
    private String department;
    private String weight;

}