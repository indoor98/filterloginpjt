package com.toy.filterloginpjt.service;

import com.toy.filterloginpjt.dto.SignInRequestDTO;
import com.toy.filterloginpjt.dto.SignInResponseDTO;
import com.toy.filterloginpjt.dto.SignUpRequestDTO;
import com.toy.filterloginpjt.dto.SignUpResponseDTO;
import com.toy.filterloginpjt.entity.User;
import com.toy.filterloginpjt.repository.UserRepository;
import com.toy.filterloginpjt.util.JwtProvider;
import com.toy.filterloginpjt.util.PayLoad;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

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

    @Transactional
    public SignInResponseDTO signIn(SignInRequestDTO requestDTO) {

        User user = userRepository.findByEmail(requestDTO.getEmail());

        if (!requestDTO.getPassword().equals(user.getPassword())) {
            throw new BadCredentialsException("잘못된 이메일 또는 비밀번호입니다. 확인해주세요");
        }

        PayLoad AtkPayLoad = PayLoad.builder()
                            .id(user.getId())
                            .email(user.getEmail())
                            .type("ATK")
                            .authority(user.getAuthority())
                            .build();

        PayLoad RtkPayLoad = PayLoad.builder()
                            .id(user.getId())
                            .email(user.getEmail())
                            .type("RTK")
                            .authority(user.getAuthority())
                            .build();

        String atk = jwtProvider.createToken(AtkPayLoad);
        String rtk = jwtProvider.createToken(RtkPayLoad);

        return new SignInResponseDTO(atk, rtk);

    }
}
