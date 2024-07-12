package com.example.employepoc.query.rest.dto;

import com.example.employepoc.command.rest.dto.BaseBean;
import com.hydatis.cqrsref.domain.BaseEntity;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a role entity stored in MongoDB.
 * This class extends {@link BaseBean} and is annotated with {@link Document} to indicate its collection name in MongoDB.
 * It encapsulates role-specific fields such as name, description, color, and grants among others.
 */
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Document(collection = "econtact")
@Builder
@ToString
public class Contact extends BaseEntity {

    private static final long	serialVersionUID	= 1L;
    private String	          emailAdress;
    private String	          emailPassword;
    private String	          smtpServer;
    private String	          phoneNumber;
    private ContactType	      contactType	     = ContactType.USER;


    @Override
    public BaseBean clone() {
        return null;
    }

    /**
     *
     */
    public enum ContactType {
        USER, SERVER, PERSON
    }
}
