package com.example.employepoc.command.rest.dto;

import com.hydatis.cqrsref.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;

/**
 * Represents a request to either create a new checking or update an existing one for a specific person.
 * This class encapsulates the data necessary for creating or updating a checking event, including the person's ID,
 * the checking ID (if updating), the date of the checking, and a custom field named "threeDaysTime".
 */
@Data
@NoArgsConstructor
@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "root")
@Builder
public class Root extends BaseEntity {
    @Id
    private long Id;
    private static final long	serialVersionUID	= 1L;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User root;



    @Override
    public BaseBean clone() {
        // TODO Auto-generated method stub
        return null;
    }

}