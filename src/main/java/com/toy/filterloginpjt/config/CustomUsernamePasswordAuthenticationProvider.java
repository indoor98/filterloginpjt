package com.toy.filterloginpjt.config;

import com.toy.filterloginpjt.entity.User;
import com.toy.filterloginpjt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomUsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserRepository userRepository; // 유저 정보를 불러오기 위함.

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        List<User> user = userRepository.findByEmail(username);

        if (user.size() > 0) {
            String DbPassword = user.get(0).getPassword();
            if (DbPassword.equals(password)) {
                return new UsernamePasswordAuthenticationToken(username, password, getGrantedAuthorities(user.get(0).getAuthority()));
            } else {
                throw new BadCredentialsException("Invalid password!");
            }
        } else {
            throw new BadCredentialsException("No user registered with this details!");
        }
    }
    private List<GrantedAuthority> getGrantedAuthorities(String authority) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(authority));
        return grantedAuthorities;
    }

    // 얘는 모르겠읍니다..
    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
