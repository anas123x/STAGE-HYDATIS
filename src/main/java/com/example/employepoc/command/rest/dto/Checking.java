package com.example.employepoc.command.rest.dto;

import lombok.*;
import org.joda.time.LocalDateTime;
import org.springframework.data.mongodb.core.mapping.Field;


import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a checking event, encapsulating all relevant data such as times, person involved, and source of the checking.
 * This class is annotated for persistence in a relational database and includes additional metadata for MongoDB storage.
 */
@Data
@NoArgsConstructor
@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "checking")
@Builder
public class Checking extends BaseBean implements Comparable<Checking> {
	@Id
	private String id;
	private static final long	  serialVersionUID	  = 1L;
	public static final int	    SECONDS_IN_1_MINUTE	= 60;
	public static final int	    MILLIS_IN_1_SECOND	= 1000;
	private LocalDateTime	      actualTime;
	private LocalDateTime	      logicalTime;
	private String	            matricule;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "data")
	@MapKeyColumn(name = "data_name")
	@Column(name = "data_value")
	private Map<String, String>	data	              = new HashMap<String, String>();
	private boolean	            directionGenerated;
	private boolean	            ignoredByCalc	      = false;
	private LocalDateTime	      userSetTime;
	private CheckingSource	    actualSource;
	private CheckingDirection	  direction;
	private boolean	            used	              = false;
	private Long	              timesheetId;
	@ManyToOne
	private Person	 person;
	@Field("checking-deleted")
	private boolean deleted=false;
	// added

	// by
	// seif

	public Person getPerson() {
		return person;
	}

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

	public CheckingDirection getDirection() {
		return direction;
	}

	public void setDirection(CheckingDirection type) {
		this.direction = type;
	}

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public boolean isDirectionGenerated() {
		return directionGenerated;
	}

	public void setDirectionGenerated(boolean directionGenerated) {
		this.directionGenerated = directionGenerated;
	}

	public LocalDateTime getActualTime() {
		return actualTime;
	}

	public void setActualTime(LocalDateTime actualTime) {
		this.actualTime = actualTime;
	}

	public LocalDateTime getUserSetTime() {
		return userSetTime;
	}

	public void setUserSetTime(LocalDateTime userSetTime) {
		this.userSetTime = userSetTime;
	}

	public LocalDateTime getLogicalTime() {
		return logicalTime;
	}

	public void setLogicalTime(LocalDateTime logicalTime) {
		this.logicalTime = logicalTime;
	}

	public LocalDateTime getTime() {
		return logicalTime == null ? (userSetTime == null ? actualTime : userSetTime) : logicalTime;
	}

	public CheckingSource getActualSource() {
		return actualSource;
	}

	public void setActualSource(CheckingSource source) {
		this.actualSource = source;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

	public boolean isIgnoredByCalc() {
		return ignoredByCalc;
	}

	public void setIgnoredByCalc(boolean ignoredByCalc) {
		this.ignoredByCalc = ignoredByCalc;
	}

	@Override
	public String toString() {
		return "Checking [Time=" + getTime() + ", directionGenerated=" + directionGenerated + ", ignoredByCalc="
		    + ignoredByCalc + ", actualSource=" + actualSource + ", person=" + (person == null ? null : person.getId())
		    + "]";
	}

	@Override
	public BaseBean clone() {
		throw new UnsupportedOperationException("Not implemented");
	}

	public Long getTimesheetId() {
		return timesheetId;
	}

	public void setTimesheetId(Long timesheetId) {
		this.timesheetId = timesheetId;
	}

	public boolean isUsed() {
		return used;
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
