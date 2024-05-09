package com.toy.filterloginpjt.service;

import com.toy.filterloginpjt.dto.SignUpRequestDTO;
import com.toy.filterloginpjt.dto.SignUpResponseDTO;
import com.toy.filterloginpjt.entity.User;
import com.toy.filterloginpjt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public SignUpResponseDTO signUp(SignUpRequestDTO requestDTO) {
        if(userRepository.existsByEmail(requestDTO.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다");
        }

        User user = User.builder()
                .email(requestDTO.getEmail())
                .name(requestDTO.getName())
                .password(requestDTO.getPassword())
                .deletedYn(false)
                .authority("ROLE_USER")
                .build();

        userRepository.save(user);
        SignUpResponseDTO responseDTO = SignUpResponseDTO.builder()
                .email(requestDTO.getEmail())
                .name(requestDTO.getName())
                .build();

        return responseDTO;
    }
}
