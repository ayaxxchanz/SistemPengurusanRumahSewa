package com.aliya.rentalmgmt.config.security;

import com.aliya.rentalmgmt.entity.User;
import com.aliya.rentalmgmt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

//@Profile("prod")
@Component
@RequiredArgsConstructor
public class PhonePwdAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String phone = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user = userRepository.findByPhone(phone).orElseThrow(
                () -> new UsernameNotFoundException("User not found for phone: " + phone)
        );
        if(passwordEncoder.matches(password, user.getPassword())){
            List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
            // not recommended to carry around the password so it is set to NULL
            return new UsernamePasswordAuthenticationToken(user, null, authorities);
        }
        else {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication){
        // to support only username and password -style of authentication in this Provider
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}