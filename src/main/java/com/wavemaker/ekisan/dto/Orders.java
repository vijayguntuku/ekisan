package com.wavemaker.ekisan.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Orders{
    private int id;
    private int total_items;
    private float total_amount;
    private Date date;
    private int delivery_address_id;
    private Date updatedAt;
    private int updatedBy;
    private String status;
    private int sellerId;

}
