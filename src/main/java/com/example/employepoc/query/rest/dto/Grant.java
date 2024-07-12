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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "grant")
@Builder
@ToString
public class Grant extends BaseEntity {

    private static final long	serialVersionUID	= 1L;
    private String	          data;
    private String	          idg;



    @Override
    public String toString() {
        return idg + "=" + data;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || idg == null || !(obj instanceof Grant)) {
            return false;
        }
        Grant g = (Grant) obj;
        if (g.idg == null || !(g.idg.equals(idg))) {
            return false;
        }
        return data != null ? data.equals(g.data) : g.data == null;
    }

    @Override
    public int hashCode() {
        if (idg == null) {
            return 0;
        }
        return (idg + "(" + data + ")").hashCode();
    }

    @Override
    public BaseBean clone() {
        return null;
    }

}

