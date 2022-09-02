package com.wavemaker.ekisan.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class User {
	 private int id;
    private String email;
    private String password;
    private String name;
    private int role;
}
