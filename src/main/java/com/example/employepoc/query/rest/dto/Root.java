package com.example.employepoc.query.rest.dto;

import com.example.employepoc.command.rest.dto.BaseBean;
import com.hydatis.cqrsref.domain.BaseEntity;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
/**
 * Represents the root entity for a specific domain, extending the generic {@link BaseEntity}.
 * This class is annotated with {@link Document} to specify its collection name in MongoDB.
 * It encapsulates a {@link User} object, representing the root user of the system.
 */
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Document(collection = "root")
@Builder
@ToString
public class Root extends BaseEntity {

    private static final long	serialVersionUID	= 1L;

    private User root;



    @Override
    public BaseBean clone() {
        // TODO Auto-generated method stub
        return null;
    }

}