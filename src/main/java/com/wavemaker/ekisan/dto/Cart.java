package com.wavemaker.ekisan.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Cart {
    private int userId;
    private int productId;
    private int quantity;
}

