package com.backend.backend.service;

import com.backend.backend.aop.LogExecutionTime;
import com.backend.backend.aop.LogParameters;
import com.backend.backend.dto.RoleDto;
import com.backend.backend.dto.UserDto;
import com.backend.backend.exception.user.RoleAlreadyExistException;
import com.backend.backend.exception.user.RoleNotFoundException;
import com.backend.backend.exception.user.UserNotFoundException;
import com.backend.backend.model.RoleEntity;
import com.backend.backend.model.UserEntity;
import com.backend.backend.repository.RoleRepository;
import com.backend.backend.repository.UserRepository;
import com.backend.backend.util.EntityToDtoConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public UserDto saveUser(UserEntity user)
    {
        UserEntity userEntity = userRepository.save(Objects.requireNonNull(user));
        return EntityToDtoConvertor.convertTo(userEntity);
    }

    @Transactional
    public UserDto updateUser(UserEntity userUpdated,Long id)
    {

        Optional<UserEntity>user = userRepository.findById(id);
        if(user.isPresent()) {
            BeanUtils.copyProperties(userUpdated, user.get());
            user.get().setId(id);
            userRepository.save(user.get());
            return EntityToDtoConvertor.convertTo(user.get());
        }
        return null;
    }

    @LogExecutionTime
    @LogParameters
    @Transactional(readOnly = true)
    public List<UserDto> findAllUsers()
   {
       final List<UserEntity> userEntities = userRepository.findAll();
       if(userEntities.isEmpty())
       {
           throw new UserNotFoundException("No user was found!");
       }
       final List<UserDto> users = userEntities.stream().map(EntityToDtoConvertor::convertTo).collect(Collectors.toList());
       return users;
   }

   @LogExecutionTime
   @LogParameters
   @Transactional(readOnly = true)
   public UserDto findUserById(Long id)
   {
       Optional<UserEntity> userEntity = userRepository.findById(id);
       if(userEntity.isPresent())
       {
           return EntityToDtoConvertor.convertTo(userEntity.get());
       }
       else
       {
           throw new UserNotFoundException("User with id " + id + " not found");
       }

   }

   @Transactional
   public void deleteUser(Long id)
   {
       Optional<UserEntity> user = userRepository.findById(id);
       if(user.isPresent())
       {
           userRepository.deleteById(id);
       }
       else
       {
           throw new UserNotFoundException("User with id " + id + " not found");
       }
   }

   @Transactional(readOnly = true)
   public List<RoleDto> findAllRoles()
   {
      final List<RoleEntity> roleEntities = roleRepository.findAll();
      if(roleEntities.isEmpty())
      {
          throw new RoleNotFoundException("No role was found!");
      }
      final List<RoleDto> roles = roleEntities.stream().map(EntityToDtoConvertor::convertTo).collect(Collectors.toList());
      return roles;
   }


   public boolean existByEmail(String email)
   {
       return userRepository.existsByEmail(email);
   }


   @Transactional(readOnly = true)
    public UserEntity findUserByEmail(String email)
    {

        /* return userRepository.findUserByEmail(email).get(); */

        return userRepository.findUserByEmail(email).orElseThrow(
                ()->new UserNotFoundException("User with email " + email + " not found"));
    }


    @Transactional
    public RoleDto saveRole(RoleEntity role)
    {
        RoleEntity roleEntity = roleRepository.save(role);
        return EntityToDtoConvertor.convertTo(roleEntity);
    }

    @Transactional
    public void addRoleToUser(String email,String roleName)
    {
        UserEntity user = userRepository.findUserByEmail(email).get();
        RoleEntity roleEntity = roleRepository.findByName(roleName).get();
        if(!user.getRoles().contains(roleEntity)) {
            user.getRoles().add(roleEntity);
        }
        else
        {
            throw new RoleAlreadyExistException("User with name "+user.getFirstName()+" already has role "+roleName);
        }
    }


    @Transactional
    public void deleteRole(Long id)
    {
        Optional<RoleEntity> role = roleRepository.findById(id);
        if(role.isPresent())
        {
            List<UserEntity> users = (List<UserEntity>) role.get().getUsers();
            for(UserEntity user:users)
            {
                user.getRoles().remove(role.get());
                userRepository.save(user);
            }
            roleRepository.deleteById(id);
        }
    }


}
