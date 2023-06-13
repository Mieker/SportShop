package com.mieker.SportShop.application.service;

import com.mieker.SportShop.application.dto.user.UserDto;
import com.mieker.SportShop.application.exception.AccessDeniedException;
import com.mieker.SportShop.application.exception.AuthenticationException;
import com.mieker.SportShop.application.mapper.UserMapper;
import com.mieker.SportShop.application.security.PasswordEncoder;
import com.mieker.SportShop.domain.model.user.Role;
import com.mieker.SportShop.domain.model.user.User;
import com.mieker.SportShop.infrastruckture.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.mieker.SportShop.application.exception.NoUserFoundException;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserDto authorizeUser(String authorizationHeader, List<Role> enabledRoles) {
        UserDto user = authenticateUser(authorizationHeader);
        checkUserAccess(user, enabledRoles);
        return user;
    }

    private UserDto authenticateUser(String authorizationHeader) {
        String encodedCredentials = authorizationHeader.substring("Basic".length()).trim();
        String credentials = new String(Base64.getDecoder().decode(encodedCredentials), StandardCharsets.UTF_8);
        String[] usernameAndPassword = credentials.split(":");

        String username;
        String password;
        try {
            username = usernameAndPassword[0];
            password = usernameAndPassword[1];
        } catch (Exception e) {
            throw new AuthenticationException("Incorrect user credentials.");
        }

        User user = userRepository.findByUsername(username);
        if (null == user) {
            throw new NoUserFoundException("Cannot find user: " + username);
        }

        boolean authorized = passwordEncoder.verifyPassword(password, user.getPassword());
        if (authorized) {
            return userMapper.map(user);
        }
        throw new AuthenticationException("Incorrect user credentials.");
    }

    private void checkUserAccess(UserDto user, List<Role> enabledRoles) {
        for (Role role : user.getAuthorities()) {
            if (enabledRoles.contains(role)) {
                return;
            }
        }
        throw new AccessDeniedException("User has no access to the source.");
    }
}
