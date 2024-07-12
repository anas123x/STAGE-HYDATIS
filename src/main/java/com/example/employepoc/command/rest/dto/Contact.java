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
@NoArgsConstructor
@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "contact")
@Builder
public class Contact extends BaseEntity {
    @Id
    private long Id;
    private static final long	serialVersionUID	= 1L;
    private String	          emailAdress;
    private String	          emailPassword;
    private String	          smtpServer;
    private String	          phoneNumber;
    private ContactType	      contactType	     = ContactType.USER;

    public ContactType getContactType() {
        return contactType;
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAdress() {
        return emailAdress;
    }

    public void setEmailAdress(String emailAdress) {
        this.emailAdress = emailAdress;
    }

    public String getEmailPassword() {
        return emailPassword;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }

    public String getSmtpServer() {
        return smtpServer;
    }

    public void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer;
    }

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
