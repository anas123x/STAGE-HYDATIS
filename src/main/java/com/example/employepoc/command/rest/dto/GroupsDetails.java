package com.example.employepoc.command.rest.dto;

import com.hydatis.cqrsref.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "GroupDetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupsDetails extends BaseEntity {
    @Id
    private String id;
    @ManyToOne
    private Group group;
    @OneToMany
    private List<Role> roles;


}
