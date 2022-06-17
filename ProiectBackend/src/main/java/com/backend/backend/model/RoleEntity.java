package com.backend.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@Table(name = "roles")
public class RoleEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "role_generator")
    @SequenceGenerator(name = "role_generator",sequenceName = "role_seq")
    private Long id;
    private String name;

    public RoleEntity(String name)
    {
        this.name = name;
    }
    @ManyToMany(mappedBy = "roles")
    private Collection<UserEntity> users = new ArrayList<>();

}
