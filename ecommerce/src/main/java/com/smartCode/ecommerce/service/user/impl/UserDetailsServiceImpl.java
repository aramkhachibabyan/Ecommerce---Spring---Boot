package com.smartCode.ecommerce.service.user.impl;

import com.smartCode.ecommerce.model.dto.user.UserDetailsImpl;
import com.smartCode.ecommerce.model.entity.user.UserEntity;
import com.smartCode.ecommerce.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity byPhoneOrEmailOrUsername = userRepository.findByPhoneOrEmailOrUsername(username, username, username);
        return UserDetailsImpl.build(byPhoneOrEmailOrUsername.getId(), username,byPhoneOrEmailOrUsername.getPassword(),
                List.of(new SimpleGrantedAuthority(byPhoneOrEmailOrUsername.getRole().getRole().getName())));
    }
}
