package com.backend.backend.util;

import com.backend.backend.dto.ProductDto;
import com.backend.backend.dto.RoleDto;
import com.backend.backend.dto.UserDto;
import com.backend.backend.model.ProductEntity;
import com.backend.backend.model.RoleEntity;
import com.backend.backend.model.UserEntity;

import java.util.Set;
import java.util.stream.Collectors;

public class EntityToDtoConvertor {

    public static UserDto convertTo(UserEntity userEntity) {
        final UserDto user = new UserDto();

        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setEmail(userEntity.getEmail());
        user.setPassword(userEntity.getPassword());
        Set<String> roles = userEntity.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toSet());
        user.setRoles(roles);

        return user;
    }

    public static RoleDto convertTo(RoleEntity roleEntity)
    {
        final RoleDto role = new RoleDto();
        role.setName(roleEntity.getName());
        Set<String> users = roleEntity.getUsers().stream().map(UserEntity::getFirstName).collect(Collectors.toSet());
        role.setUsers(users);
        return role;
    }

    public static ProductDto convertTo(ProductEntity productEntity)
    {
        final ProductDto product = new ProductDto();
        product.setName(productEntity.getName());
        product.setDescription(productEntity.getDescription());
        product.setPrice(productEntity.getPrice());
        return product;
    }

}
