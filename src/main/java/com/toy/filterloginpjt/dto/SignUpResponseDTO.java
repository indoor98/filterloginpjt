package com.toy.filterloginpjt.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SignUpResponseDTO {

    private String email;
    private String name;

    @Builder
    private SignUpResponseDTO(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
