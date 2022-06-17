package com.backend.backend.user;

import com.backend.backend.dto.ProductDto;
import com.backend.backend.dto.UserDto;
import com.backend.backend.exception.product.ProductNotFoundException;
import com.backend.backend.exception.user.RoleAlreadyExistException;
import com.backend.backend.exception.user.UserNotFoundException;
import com.backend.backend.model.ProductEntity;
import com.backend.backend.model.RoleEntity;
import com.backend.backend.model.UserEntity;
import com.backend.backend.repository.RoleRepository;
import com.backend.backend.repository.UserRepository;
import com.backend.backend.service.UserService;
import com.backend.backend.util.EntityToDtoConvertor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    private UserEntity u1, u2;
    private UserDto dto1,dto2;
    private RoleEntity r1,r2;

    @BeforeEach
    public void init() {

         r1 = new RoleEntity("user");
         u1 = new UserEntity();
         u2 = new UserEntity();
         u1.setFirstName("u1_first");
         u1.setLastName("u1_last");
         u1.setEmail("u1@yahoo.com");
         u1.setPassword("123");
         u1.setRoles(Arrays.asList(r1));
         u2.setFirstName("u2_first");
         u2.setLastName("u2_last");
         u2.setEmail("u2@yahoo.com");
         u2.setPassword("123");
         u2.setRoles(Arrays.asList(r1));
         dto1 = EntityToDtoConvertor.convertTo(u1);
         dto2 = EntityToDtoConvertor.convertTo(u2);
    }

    @Test
    void findAllUsersTestSuccess(){
        List<UserEntity> users = Arrays.asList(u1, u2);
        when(userRepository.findAll()).thenReturn(users);
        List<UserDto> response = userService.findAllUsers();
        assertEquals(users.size(),response.size());
    }

    @Test
    void findAllUsersTestFailed(){
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(UserNotFoundException.class, ()-> userService.findAllUsers());
    }

    @Test
    void saveUserTest(){
        when(userRepository.save(any(UserEntity.class))).thenReturn(u1);
        assertEquals(dto1, userService.saveUser(u1));
    }

    @Test
    void updateUserTestSuccess(){
        UserEntity u3 = u1;
        u3.setEmail("u13@yahoo.com");
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(u1));
        assertEquals(EntityToDtoConvertor.convertTo(u3), userService.updateUser(u3, 1l));
    }

    @Test
    void findUserByIdTestSuccess(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(u1));
        assertEquals(dto1, userService.findUserById(1l));
    }

    @Test
    void findUserByIdTestFailed(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.findUserById(1l));
    }

    @Test
    void deleteUserTest(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(u1));
        assertDoesNotThrow(()-> userService.deleteUser(1l));
    }

    @Test
    void addRoleToUserTestFailed(){
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(u1));
        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(r1));
        assertThrows(RoleAlreadyExistException.class, ()-> userService.addRoleToUser("u1@yahoo.com","user"));
    }


}
