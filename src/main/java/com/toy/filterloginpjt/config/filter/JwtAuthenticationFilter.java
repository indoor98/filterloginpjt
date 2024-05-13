package com.toy.filterloginpjt.config.filter;

import com.toy.filterloginpjt.util.JwtProvider;
import com.toy.filterloginpjt.util.PayLoad;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.auth.Subject;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    public JwtAuthenticationFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if(authorization != null) {
            String jwt = authorization.substring(7);
            try {
                PayLoad payLoad = jwtProvider.getPayLoad(jwt);
                String requestURI = request.getRequestURI();

            }
    }
}
