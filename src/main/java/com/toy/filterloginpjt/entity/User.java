package com.toy.filterloginpjt.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name="users")
@Entity
@Getter
@NoArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable = false)
    private Long id;

    private String name;

    private String email;

    private String password;

    private String authority;

    private Boolean deletedYn;

    @Builder
    public User(String name, String email, String password, String authority, Boolean deletedYn ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.authority = authority;
        this.deletedYn = deletedYn;
    }

}
