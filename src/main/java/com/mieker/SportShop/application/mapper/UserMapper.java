package com.mieker.SportShop.application.mapper;

import com.mieker.SportShop.application.dto.user.UserDto;
import com.mieker.SportShop.domain.model.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto map(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getAuthorities());
    }
}
