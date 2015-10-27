package share.apk.server.dto;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import share.apk.server.exceptions.NotNullException;

@Entity
public abstract class Packet {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	@ManyToOne
	// (fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.FALSE)
	User fromUser;
	@ManyToOne
	// (fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.FALSE)
	User toUser;
	@Enumerated(EnumType.STRING)
	PacketStatus status;
	@Temporal(TemporalType.TIMESTAMP)
	Date timeStamp;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getFromUser() {
		return fromUser;
	}

	public void setFromUser(User fromUser) throws NotNullException {
		if (fromUser != null) {
			this.fromUser = fromUser;
		} else {
			throw new NotNullException("From User cannot be null");
		}
	}

	public User getToUser() {
		return toUser;
	}

	public void setToUser(User toUser) throws NotNullException {
		if (toUser != null) {
			this.toUser = toUser;
		} else {
			throw new NotNullException("To User cannot be null");
		}
	}

	public PacketStatus getStatus() {
		return status;
	}

	public void setStatus(PacketStatus fileStatus) throws NotNullException {
		if (fileStatus != null && !fileStatus.equals("")) {
			this.status = fileStatus;
		} else {
			throw new NotNullException("Status cannot be null or empty");
		}
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) throws NotNullException {
		this.timeStamp = timeStamp;
	}

}
