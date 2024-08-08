package com.example.employepoc.command.rest.dto;

import com.hydatis.cqrsref.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Permissions extends BaseEntity {
    @Id
    private String id;
    private String name;
    private String description;
    @ManyToOne
    private Role role;
}

