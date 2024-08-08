package com.example.employepoc.command.rest.dto;

import com.hydatis.cqrsref.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity class representing a Role in the system.
 * Roles are associated with various attributes including their name, description, permissions etc.
 */


@Entity
@Table(name = "Role")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role extends BaseEntity {

    @Id
    private String id;

    @NotBlank(message = "name is required")
    private String name;

    private String description;

    private Boolean onDisabled;
    @OneToMany
    private Set<Permissions> permissions = new HashSet<>();

}

