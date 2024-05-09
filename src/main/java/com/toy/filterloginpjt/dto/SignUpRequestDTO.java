package com.toy.filterloginpjt.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SignUpRequestDTO {

    private String email;
    private String name;
    private String password;

    @Builder
    public SignUpRequestDTO(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
