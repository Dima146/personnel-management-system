package com.company.managementsystem.controller.dto.converter;

import com.company.managementsystem.controller.dto.UserDto;
import com.company.managementsystem.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter implements DtoConverter<User, UserDto> {

    @Override
    public User convertToEntity(UserDto dto) {
        User entity = new User();
        entity.setId(dto.getId());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEnabled(true);
        entity.setEmail(dto.getEmail());
        return entity;
    }

    @Override
    public UserDto convertToDto(User entity) {
        return null;
    }
}
