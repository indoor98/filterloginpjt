package com.toy.filterloginpjt.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SignInResponseDTO {

    private final String atk;
    private final String rtk;

    @Builder
    public SignInResponseDTO(String atk, String rtk) {
        this.atk = atk;
        this.rtk = rtk;
    }
}
