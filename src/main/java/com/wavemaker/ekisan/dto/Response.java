package com.wavemaker.ekisan.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Response<T> {
    private boolean success;
    private String message;
    private int statusCode;
    T data;

}
