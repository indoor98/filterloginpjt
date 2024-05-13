package com.toy.filterloginpjt.config.filter;

import com.toy.filterloginpjt.util.JwtProvider;
import com.toy.filterloginpjt.util.PayLoad;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
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

        if (authorization != null) {
            String jwt = authorization.substring(7);
            try {
                PayLoad payLoad = jwtProvider.getPayLoad(jwt);
                System.out.println("vlfxj"+payLoad);
//                String requestURI = request.getRequestURI();
                String email = payLoad.getEmail();
                String authority = payLoad.getAuthority();

                // 여기서부터 중요!!
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        email, null, AuthorityUtils.commaSeparatedStringToAuthorityList(authority));

                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (JwtException e) {
                request.setAttribute("exception", e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    };


    /**
     * 로그인 시 해당 필터를 거치지 않도 설정합니다.
     * ( return 값이 true가 되는 조건은 필터를 거치지 않습니다.)
     */

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().equals("/api/v1/auth/signin");
    }

}
