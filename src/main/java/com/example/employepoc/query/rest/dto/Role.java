package com.example.employepoc.query.rest.dto;

import com.example.employepoc.command.rest.dto.BaseBean;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Document(collection = "role")
@Builder
@ToString

/**
 * Represents a role entity stored in MongoDB.
 * This class extends {@link BaseBean} and is annotated with {@link Document} to indicate its collection name in MongoDB.
 * It encapsulates role-specific fields such as name, description, color, and grants among others.
 */
public class Role extends BaseBean {
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
