package com.simplyfly.mapper;

import com.simplyfly.dto.UserDTO;
import com.simplyfly.model.Role;
import com.simplyfly.model.user;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

    // Convert user entity to UserDTO
    public static UserDTO toDTO(user userEntity) {
        if (userEntity == null) {
            return null;
        }

        // Extract role names as a comma-separated string
        String role = userEntity.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.joining(", "));

        return new UserDTO(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getName(),
                userEntity.getEmail(),
                role,
                null // We typically do not include passwords in DTOs for security reasons
        );
    }

    // Convert UserDTO to user entity
    public static user toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        user userEntity = new user();
        userEntity.setId(userDTO.getId());
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setName(userDTO.getName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(userDTO.getPassword()); // Include password if required for creating/updating user
        return userEntity;
    }

    // Convert a set of user entities to a set of UserDTOs
    public static Set<UserDTO> toDTOSet(Set<user> userEntities) {
        return userEntities.stream().map(UserMapper::toDTO).collect(Collectors.toSet());
    }

    // Convert a set of UserDTOs to a set of user entities
    public static Set<user> toEntitySet(Set<UserDTO> userDTOs) {
        return userDTOs.stream().map(UserMapper::toEntity).collect(Collectors.toSet());
    }
}
