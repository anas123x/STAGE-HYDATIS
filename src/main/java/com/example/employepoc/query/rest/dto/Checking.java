package com.example.employepoc.query.rest.dto;

import com.example.employepoc.command.rest.dto.BaseBean;
import com.hydatis.cqrsref.domain.BaseEntity;
import lombok.*;
import org.joda.time.LocalDateTime;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a role entity stored in MongoDB.
 * This class extends {@link BaseBean} and is annotated with {@link Document} to indicate its collection name in MongoDB.
 * It encapsulates role-specific fields such as name, description, color, and grants among others.
 */
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Document(collection = "chekings")
@Builder
@ToString
public class Checking extends BaseEntity implements Comparable<Checking> {
	@Id
	private String id;
	private static final long	  serialVersionUID	  = 1L;
	public static final int	    SECONDS_IN_1_MINUTE	= 60;
	public static final int	    MILLIS_IN_1_SECOND	= 1000;
	private LocalDateTime	      actualTime;
	private LocalDateTime	      logicalTime;
	private String	            matricule;
	@Field("checkingData")

	private Map<String, String>	data	              = new HashMap<String, String>();
	private boolean	            directionGenerated;
	private boolean	            ignoredByCalc	      = false;
	private LocalDateTime	      userSetTime;
	private CheckingSource	    actualSource;
	private CheckingDirection	  direction;
	private boolean	            used	              = false;
	private Long	              timesheetId;
	@Field("checking_deleted")
	private boolean deleted=false;

	private Person person;	                                           // added
	@DBRef
	private User user ;	                                           // added
	// by
	// seif


	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((actualSource == null) ? 0 : actualSource.hashCode());
		result = prime * result + ((actualTime == null) ? 0 : actualTime.hashCode());
		result = prime * result + ((logicalTime == null) ? 0 : logicalTime.hashCode());
		result = prime * result + ((person == null) ? 0 : person.hashCode());
		result = prime * result + ((timesheetId == null) ? 0 : timesheetId.hashCode());
		result = prime * result + ((userSetTime == null) ? 0 : userSetTime.hashCode());
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
		Checking other = (Checking) obj;
		if (actualSource != other.actualSource) {
			return false;
		}
		if (actualTime == null) {
			if (other.actualTime != null) {
				return false;
			}
		} else if (other.actualTime == null
		    || (actualTime.toDateTime().getMillis() / SECONDS_IN_1_MINUTE * MILLIS_IN_1_SECOND) != (other.actualTime
		        .toDateTime().getMillis() / SECONDS_IN_1_MINUTE * MILLIS_IN_1_SECOND)) {
			return false;
		}
		if (logicalTime == null) {
			if (other.logicalTime != null) {
				return false;
			}
		} else if (other.logicalTime == null
		    || (logicalTime.toDateTime().getMillis() / SECONDS_IN_1_MINUTE * MILLIS_IN_1_SECOND) != (other.logicalTime
		        .toDateTime().getMillis() / SECONDS_IN_1_MINUTE * MILLIS_IN_1_SECOND)) {
			return false;
		}
		if (userSetTime == null) {
			if (other.userSetTime != null) {
				return false;
			}
		} else if (other.userSetTime == null
		    || (userSetTime.toDateTime().getMillis() / SECONDS_IN_1_MINUTE * MILLIS_IN_1_SECOND) != (other.userSetTime
		        .toDateTime().getMillis() / SECONDS_IN_1_MINUTE * MILLIS_IN_1_SECOND)) {
			return false;
		}
		if (person == null) {
			if (other.person != null) {
				return false;
			}
		} else if (!person.equals(other.person)) {
			return false;
		}
		if (timesheetId == null) {
			if (other.timesheetId != null) {
				return false;
			}
		} else if (!timesheetId.equals(other.timesheetId)) {
			return false;
		}

		return true;
	}


	public void setDirection(CheckingDirection type) {
		this.direction = type;
	}


	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}


	public void setDirectionGenerated(boolean directionGenerated) {
		this.directionGenerated = directionGenerated;
	}


	public void setActualTime(LocalDateTime actualTime) {
		this.actualTime = actualTime;
	}


	public void setUserSetTime(LocalDateTime userSetTime) {
		this.userSetTime = userSetTime;
	}


	public void setLogicalTime(LocalDateTime logicalTime) {
		this.logicalTime = logicalTime;
	}

	public LocalDateTime getTime() {
		return logicalTime == null ? (userSetTime == null ? actualTime : userSetTime) : logicalTime;
	}


	public void setActualSource(CheckingSource source) {
		this.actualSource = source;
	}


	public void setData(Map<String, String> data) {
		this.data = data;
	}


	public void setIgnoredByCalc(boolean ignoredByCalc) {
		this.ignoredByCalc = ignoredByCalc;
	}



	@Override
	public BaseBean clone() {
		throw new UnsupportedOperationException("Not implemented");
	}


	public void setTimesheetId(Long timesheetId) {
		this.timesheetId = timesheetId;
	}



	public void setUsed(boolean used) {
		this.used = used;
	}

	@Override
	public int compareTo(Checking o) {
		return getTime().compareTo(o.getTime());
	}

	public enum CheckingDirection {
		IN, OUT
	}

	public enum CheckingSource {
		HARDWARE, USER, CALCULUS, IMPORT
	}
}
