package com.example.employepoc.command.rest.dto;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
/**
 * Entity class representing a password storage mechanism for users.
 * This class is used to store passwords along with their associated user IDs and the date the password was set.
 * It extends {@link BaseBean} to inherit base entity functionalities.
 */
@Data
@NoArgsConstructor
@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "password_store")
@Builder
public class PasswordStore extends BaseBean {
    @Id
    private long Id;
    private Long	 userId;
    private String	password;
    private Date	 dateP;


    @Override
    public BaseBean clone() {
        return null;
    }
}
