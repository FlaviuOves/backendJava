package com.backend.backend.controller;

import com.backend.backend.dto.UserDto;
import com.backend.backend.model.UserEntity;
import com.backend.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-api")
@RequiredArgsConstructor
public class UserController {

    private final UserService persistenceService;


    @GetMapping("/users")
    public ResponseEntity findAllUsers()
    {
        List<UserDto> users = persistenceService.findAllUsers();
        return ResponseEntity.ok().body(users);
    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/user")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserEntity userEntity)
    {
        UserDto user = persistenceService.saveUser(userEntity);
        return ResponseEntity.ok().body(user);
    }

    @PreAuthorize("hasAuthority('admin')")
    @PutMapping("/user/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserEntity userEntity,@PathVariable Long id)
    {
        UserDto user = persistenceService.updateUser(userEntity,id);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto>getUser(@PathVariable Long id)
    {
        UserDto user = persistenceService.findUserById(id);
        return ResponseEntity.ok().body(user);
    }

    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id)
    {
        persistenceService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}

