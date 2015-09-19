package share.apk.android.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import share.apk.android.exceptions.NotNullException;

@Entity
public abstract class ApkSharePacket {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	@ManyToOne
	ApkShareUser fromUser;
	@ManyToOne
	ApkShareUser toUser;
	@Enumerated(EnumType.STRING)
	ApkShareStatus fileStatus;
	@Temporal(TemporalType.TIMESTAMP)
	Date timeStamp;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ApkShareUser getFromUser() {
		return fromUser;
	}

	public void setFromUser(ApkShareUser fromUser) throws NotNullException {
		if (fromUser != null) {
			this.fromUser = fromUser;
		} else {
			throw new NotNullException("From User cannot be null");
		}
	}

	public ApkShareUser getToUser() {
		return toUser;
	}

	public void setToUser(ApkShareUser toUser) throws NotNullException {
		if (toUser != null) {
			this.toUser = toUser;
		} else {
			throw new NotNullException("To User cannot be null");
		}
	}

	public ApkShareStatus getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(ApkShareStatus fileStatus)
			throws NotNullException {
		if (fileStatus != null && !fileStatus.equals("")) {
			this.fileStatus = fileStatus;
		} else {
			throw new NotNullException("File Status cannot be null or empty");
		}
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) throws NotNullException {
		this.timeStamp = timeStamp;
	}

}
