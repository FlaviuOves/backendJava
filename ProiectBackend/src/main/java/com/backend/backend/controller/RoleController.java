package com.backend.backend.controller;

import com.backend.backend.dto.RoleDto;
import com.backend.backend.form.RoleToUserForm;
import com.backend.backend.model.RoleEntity;
import com.backend.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role-api")
@RequiredArgsConstructor
public class RoleController {

    private final UserService persistenceService;

    @PostMapping("/role")
    public ResponseEntity<RoleDto> saveRole(@RequestBody RoleEntity roleEntity)
    {
        RoleDto role = persistenceService.saveRole(roleEntity);
        return ResponseEntity.ok().body(role);
    }

    @GetMapping("/roles")
    public ResponseEntity getRoles()
    {
        List<RoleDto> roles = persistenceService.findAllRoles();
        return ResponseEntity.ok().body(roles);

    }

    @PostMapping("/role/user")
    public ResponseEntity addRoleToUser(@RequestBody RoleToUserForm form)
    {
        String email = form.getEmail();
        String roleName = form.getRoleName();
        persistenceService.addRoleToUser(email,roleName);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping("/role/{id}")
    public ResponseEntity deleteRole(@PathVariable Long id)
    {
        persistenceService.deleteRole(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }


}
