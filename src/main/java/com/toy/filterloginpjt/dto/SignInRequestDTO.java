package com.toy.filterloginpjt.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SignInRequestDTO {

    private final String email;
    private final String password;

    @Builder
    public SignInRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
