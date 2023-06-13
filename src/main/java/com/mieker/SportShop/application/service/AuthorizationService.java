package com.mieker.SportShop.application.service;

import com.mieker.SportShop.application.dto.user.UserDto;
import com.mieker.SportShop.domain.model.user.Role;

import java.util.List;

public interface AuthorizationService {

    UserDto authorizeUser(String authorizationHeader, List<Role> enabledRoles);
}
