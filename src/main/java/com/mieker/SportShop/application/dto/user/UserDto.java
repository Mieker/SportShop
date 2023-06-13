package com.mieker.SportShop.application.dto.user;

import com.mieker.SportShop.domain.model.user.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    private String id;
    private String username;
    private Set<Role> authorities;
}
