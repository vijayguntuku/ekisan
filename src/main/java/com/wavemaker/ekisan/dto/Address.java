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

public class Address {
    private int id;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String country;
    private float pincode;
    private Date updatedAt;
    private int updatedBy;
    private int address_type;


}
