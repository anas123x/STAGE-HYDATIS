package com.example.employepoc.command.rest.dto;

import com.hydatis.cqrsref.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a role within the system, defining permissions and grants associated with it.
 * This entity extends {@link BaseEntity} to include common entity fields like ID and audit information.
 * Roles are used to manage access control within the application, allowing for a flexible permission scheme.
 */
@Data
@NoArgsConstructor
@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "role")
@Builder
public class Role extends BaseEntity {
    @javax.persistence.Id
    private Long Id;
    public static final transient Role	ROOT;
    private static final long	         serialVersionUID	= 1L;

    static {
        ROOT = new Role();
        ROOT.setId(-1L);
        ROOT.setName("root");
    }

    private String	                   name;
    private String	                   description;
    private long	                     color	          = 0;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Grant>	               grants	          = new HashSet<Grant>();


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (int) (color ^ (color >>> 32));
        result = prime * result + ((grants == null) ? 0 : grants.hashCode());
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
        Role other = (Role) obj;
        if (color != other.color) {
            return false;
        }
        if (grants == null) {
            if (other.grants != null) {
                return false;
            }
        } else if (!grants.equals(other.grants)) {
            return false;
        }
        return true;
    }

    @Override
    public BaseBean clone() {
        throw new UnsupportedOperationException("Not implemented");
    }
}
