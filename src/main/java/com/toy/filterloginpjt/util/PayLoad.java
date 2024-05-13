package com.toy.filterloginpjt.util;

import lombok.Builder;
import lombok.Getter;


/**
 * JWT PayLoad에 담기 위한 데이터 객체입니다.
 */
@Getter
public class PayLoad {

    private final Long id; // user_id
    private final String email;  // user email
    private final String type;  // ATK(AccessToken) or (RTK)(RefreshToken)
    private final String authority;

    @Builder
    public PayLoad(Long id, String email, String type, String authority) {
        this.id = id;
        this.email = email;
        this.type = type;
        this.authority = authority;
    }
}
