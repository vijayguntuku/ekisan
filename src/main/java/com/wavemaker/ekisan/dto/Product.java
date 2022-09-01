package com.wavemaker.ekisan.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {
    private int id;
    private String name;
    private String description;
    private float price;
    private String image;
    private int productCategoryId;
    private String productCategoryName;
    private int updatedBy;
    private String status;
}
