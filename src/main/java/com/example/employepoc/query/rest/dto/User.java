package com.example.employepoc.query.rest.dto;

import com.example.employepoc.command.rest.dto.BaseBean;
import com.example.employepoc.command.rest.dto.Person;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
/**
 * Represents a user entity stored in MongoDB.
 * This class extends {@link BaseBean} and is annotated with {@link Document} to indicate its collection name in MongoDB.
 * It includes user-specific fields such as login, password, email, and roles among others.
 */
@Getter
@Setter
@AllArgsConstructor
@Document(collection = "users")
@Builder
@ToString
public class User extends BaseBean {
    private static final long	serialVersionUID	  = 1L;
    public static final int	  ACTIVE_CONTRACT	    = 1231;
    public static final int	  NOT_ACTIVE_CONTRACT	= 1237;
    private String	          login;
    /*
     * @Autowired
     */
    private String	          password;
    private String	          email;
    private Contact contact;
    // This field will be used on workflow
    private String	          matricule;
    private List<Role>	      roles	              = new ArrayList<Role>(); // added
    // by seif
    
    /*
     * added by maher
     */
    // unique identifier that cannot be changed
    private String	          cn;
    // account state
    private boolean	          isActive	          = true;

    private com.example.employepoc.command.rest.dto.Person person;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }



    @Override
    public String toString() {
        return "User(" + login + ")";
    }

    @Override
    public BaseBean clone() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((contact == null) ? 0 : contact.hashCode());
        result = prime * result + (isActive ? ACTIVE_CONTRACT : NOT_ACTIVE_CONTRACT);
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((roles == null) ? 0 : roles.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;
        if (contact == null) {
            if (other.contact != null) {
                return false;
            }
        } else if (!contact.equals(other.contact)) {
            return false;
        }
        if (isActive != other.isActive) {
            return false;
        }
        if (login == null) {
            if (other.login != null) {
                return false;
            }
        } else if (!login.equals(other.login)) {
            return false;
        }
        if (roles == null) {
            if (other.roles != null) {
                return false;
            }
        } else if (!roles.equals(other.roles)) {
            return false;
        }
        return true;
    }

}
