package com.toy.filterloginpjt.controller;

import com.toy.filterloginpjt.dto.SignInRequestDTO;
import com.toy.filterloginpjt.dto.SignInResponseDTO;
import com.toy.filterloginpjt.dto.SignUpRequestDTO;
import com.toy.filterloginpjt.dto.SignUpResponseDTO;
import com.toy.filterloginpjt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/v1/auth/signup")
    public ResponseEntity<SignUpResponseDTO> signUp(@RequestBody SignUpRequestDTO requestDTO) {
        SignUpResponseDTO responseDTO = userService.signUp(requestDTO);
        return new ResponseEntity<SignUpResponseDTO>(responseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/api/v1/auth/signin")
    public ResponseEntity<SignInResponseDTO> signIn(@RequestBody SignInRequestDTO requestDTO) {
        SignInResponseDTO responseDTO = userService.signIn(requestDTO);

        return new ResponseEntity<SignInResponseDTO>(responseDTO, HttpStatus.ACCEPTED);
    }

    @PostMapping("/")
    public String home() {
        return "hello";
    }

    @RequestMapping("/success")
    public String success() {
        return "success";
    }
}