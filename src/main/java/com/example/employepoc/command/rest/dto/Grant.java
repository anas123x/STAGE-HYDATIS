package com.example.employepoc.command.rest.dto;

import com.hydatis.cqrsref.domain.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * Entity class representing a password storage mechanism for users.
 * This class is used to store passwords along with their associated user IDs and the date the password was set.
 * It extends {@link BaseBean} to inherit base entity functionalities.
 */
@Data
@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "grant")
@Builder
public class Grant extends BaseEntity {
    @Id
    private long Id;
    private static final long	serialVersionUID	= 1L;
    private String	          data;
    private String	          idg;

    public Grant() {

    }

    public Grant(String idg) {
        super();
        this.idg = idg;
    }

    public String getIdg() {
        return idg;
    }

    public void setIdg(String idg) {
        this.idg = idg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Grant withId(String idg) {
        this.idg = idg;
        return this;
    }

    public Grant withData(String data) {
        this.data = data;
        return this;
    }

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

