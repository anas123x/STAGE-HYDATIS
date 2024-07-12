package com.example.employepoc.command.rest.dto;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a request to either create a new checking or update an existing one for a specific person.
 * This class encapsulates the data necessary for creating or updating a checking event, including the person's ID,
 * the checking ID (if updating), the date of the checking, and a custom field named "threeDaysTime".
 */
@Data
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Builder
public class User extends BaseBean {
    @Id
    private long Id;
    private static final long	serialVersionUID	  = 1L;
    public static final int	  ACTIVE_CONTRACT	    = 1231;
    public static final int	  NOT_ACTIVE_CONTRACT	= 1237;
    private String	          login;
    /*
     * @Autowired
     */
    private String	          password;
    private String	          email;
    @OneToOne

    private Contact	          contact;
    // This field will be used on workflow
    private String	          matricule;
    @OneToMany
    private List<Role>	      roles	              = new ArrayList<Role>(); // added
    // by seif

    /*
     * added by maher
     */
    // unique identifier that cannot be changed
    private String	          cn;
    // account state
    private boolean	          isActive	          = true;
    @OneToOne

    private Person	          person;
    @OneToMany
    @JoinTable(
            name = "user_checkings",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "checking_id")
    )
    private List<Checking> checkings = new ArrayList<>();



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
