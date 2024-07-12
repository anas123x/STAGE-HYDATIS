package com.example.employepoc.query.rest.dto;

import com.example.employepoc.command.rest.dto.BaseBean;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Represents a role entity stored in MongoDB.
 * This class extends {@link BaseBean} and is annotated with {@link Document} to indicate its collection name in MongoDB.
 * It encapsulates role-specific fields such as name, description, color, and grants among others.
 */
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Document(collection = "password_store")
@Builder
@ToString
public class PasswordStore extends BaseBean {
    private Long	 userId;
    private String	password;
    private Date	 dateP;


    @Override
    public BaseBean clone() {
        return null;
    }
}
