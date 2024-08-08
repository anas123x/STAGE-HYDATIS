package com.example.employepoc.command.rest.dto;

import com.hydatis.cqrsref.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "UserDetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersDetails extends BaseEntity {
    @Id
    private String id;
    @OneToOne
    private User user;
    @OneToMany
    private List<Role> roles;
    @OneToMany
    private List<GroupsDetails> group;
}
